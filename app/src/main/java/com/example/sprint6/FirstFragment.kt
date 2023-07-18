package com.example.sprint6


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sprint6.databinding.FragmentFirstBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await


class FirstFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var heroeAdapter: HeroeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBreed.setOnQueryTextListener(this)
    }

    private fun initCharacter(
        heroes: List<CharacterResponse>,
        powerStats: List<CharacterPowerStats>,
        images: List<CharacterImage>
    ) {
        // Combina los datos de CharacterResponse, CharacterPowerStats y CharacterImage según el ID del personaje
        val combinedData = mutableListOf<Triple<CharacterResponse, CharacterPowerStats, CharacterImage>>()
        for (i in heroes.indices) {
            val character = heroes[i]
            val powerStat = powerStats[i]
            val image = images.find { it.id == character.id }
            if (image != null) {
                combinedData.add(Triple(character, powerStat, image))
            }
        }

        // Configura el adaptador con los datos combinados
        heroeAdapter = HeroeAdapter(combinedData)
        binding.rvHeroe.setHasFixedSize(true)
        binding.rvHeroe.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHeroe.adapter = heroeAdapter

        // Establece el onItemClickListener en el adaptador
        heroeAdapter.setOnItemClickListener { character ->
            val bundle = Bundle().apply {
                putSerializable("selectedCharacter", character)
            }
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment, bundle)
        }
    }

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val searchResponse = RetrofitClient.getClient().create(APIService::class.java)
                    .searchCharacterByName(query).await()

                val powerStatsList = mutableListOf<CharacterPowerStats>()
                val imageList = mutableListOf<CharacterImage>()

                if (searchResponse.response == "success") {
                    for (character in searchResponse.results) {
                        val powerStatsResponse =
                            RetrofitClient.getClient().create(APIService::class.java)
                                .getPowerStatsById(character.id).await()
                        powerStatsResponse?.let {
                            powerStatsList.add(it)
                        }

                        val imageResponse =
                            RetrofitClient.getClient().create(APIService::class.java)
                                .getImageById(character.id).await()
                        imageResponse?.let {
                            imageList.add(it)
                        }
                    }
                }

                requireActivity().runOnUiThread {
                    if (searchResponse.response == "success") {
                        initCharacter(searchResponse.results, powerStatsList, imageList)
                    } else {
                        showErrorDialog()
                    }
                    hideKeyboard()
                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    showErrorDialog()
                    hideKeyboard()
                }
            }
        }
    }

    private fun showErrorDialog() {
        // Implementa el diálogo de error aquí
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchByName(query.lowercase())
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // No es necesario implementar esta función en este caso
        return true
    }

    private fun hideKeyboard() {
        // Implementa el código para ocultar el teclado virtual aquí
    }
}
