package uz.pop.astroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.pop.astroidradar.Asteroid
import uz.pop.astroidradar.databinding.ListItemBinding

class AsteroidAdapter: ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroidRadar = getItem(position)
        holder.bind(asteroidRadar)
    }

    class AsteroidViewHolder(private val binding: ListItemBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(asteroidRadar: Asteroid){
                    binding.asteroid = asteroidRadar
                    binding.name.text = asteroidRadar.codename
                    binding.approachDate.text = asteroidRadar.closeApproachDate
                    binding.executePendingBindings()
                }
            }

   companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>(){
       override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
           return oldItem == newItem
       }

       override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
         return  oldItem.id == newItem.id
       }
   }
}