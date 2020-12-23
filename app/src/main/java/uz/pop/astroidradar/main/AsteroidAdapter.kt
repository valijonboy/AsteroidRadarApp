package uz.pop.astroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.pop.astroidradar.database.DatabaseAsteroid
import uz.pop.astroidradar.databinding.ListItemBinding

class AsteroidAdapter: ListAdapter<DatabaseAsteroid, AsteroidAdapter.AsteroidViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val databaseAsteroid = getItem(position)
        holder.bind(databaseAsteroid)
    }

    class AsteroidViewHolder(private val binding: ListItemBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(database: DatabaseAsteroid){
                    binding.database = database
                    binding.name.text = database.codename
                    binding.approachDate.text = database.closeApproachDate
                    binding.executePendingBindings()
                }
            }

   companion object DiffCallback : DiffUtil.ItemCallback<DatabaseAsteroid>(){
       override fun areItemsTheSame(oldItem: DatabaseAsteroid, newItem: DatabaseAsteroid): Boolean {
           return oldItem == newItem
       }

       override fun areContentsTheSame(oldItem: DatabaseAsteroid, newItem: DatabaseAsteroid): Boolean {
         return  oldItem.id == newItem.id
       }
   }
}