package com.example.sprint6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sprint6.databinding.ItemHeroeBinding
import com.squareup.picasso.Picasso

class HeroeAdapter(val heroes: List<Triple<CharacterResponse, CharacterPowerStats, CharacterImage>>) :
    RecyclerView.Adapter<HeroeAdapter.ViewHolder>() {

    private var clickListener: ((CharacterResponse) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHeroeBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = heroes[position]
        holder.bind(item.first, item.third)
    }

    override fun getItemCount(): Int {
        return heroes.size
    }

    fun setOnItemClickListener(listener: (CharacterResponse) -> Unit) {
        clickListener = listener
    }

    inner class ViewHolder(private val binding: ItemHeroeBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var character: CharacterResponse

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(character: CharacterResponse, characterImage: CharacterImage) {
            this.character = character
            binding.tvSuperHero.text = character.name
            Picasso.get().load(characterImage.url).into(binding.ivHeroImage)
        }

        override fun onClick(v: View) {
            clickListener?.invoke(character)
        }
    }
}
