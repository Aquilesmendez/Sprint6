package com.example.sprint6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sprint6.databinding.FragmentSecondBinding
import com.squareup.picasso.Picasso

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private lateinit var selectedCharacter: Triple<CharacterResponse, CharacterPowerStats, CharacterImage>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el personaje seleccionado del argumento o del método setter
        selectedCharacter = arguments?.getParcelable("selectedCharacter") ?: return

        // Mostrar los powerstats en la interfaz de usuario
        binding.tvName.text = selectedCharacter.first.name
        binding.tvIntelligence.text = selectedCharacter.second.intelligence
        binding.tvStrength.text = selectedCharacter.second.strength
        binding.tvSpeed.text = selectedCharacter.second.speed
        binding.tvDurability.text = selectedCharacter.second.durability
        binding.tvPower.text = selectedCharacter.second.power
        binding.tvCombat.text = selectedCharacter.second.combat

        // Mostrar la imagen del personaje utilizando Picasso u otra biblioteca similar
        Picasso.get().load(selectedCharacter.third.url).into(binding.ivCharacterImage)
    }
}
