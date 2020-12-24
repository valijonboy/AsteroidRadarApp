package uz.pop.astroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.pop.astroidradar.Asteroid
import uz.pop.astroidradar.database.DatabaseAsteroid
import uz.pop.astroidradar.databinding.ListItemBinding

class AsteroidAdapter: ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(DiffCallback) {

    var asteroids : List<Asteroid> = emptyList()
    set(value) {
        field = value

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid)
    }

    class AsteroidViewHolder(private val binding: ListItemBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(asteroid: Asteroid){
                    binding.asteroid = asteroid
                    binding.name.text = asteroid.codename
                    binding.approachDate.text = asteroid.closeApproachDate
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