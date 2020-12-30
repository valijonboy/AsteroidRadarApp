package uz.pop.astroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.pop.astroidradar.R
import uz.pop.astroidradar.database.AsteroidDatabase
import uz.pop.astroidradar.databinding.FragmentMainBinding
import uz.pop.astroidradar.repository.AsteroidRepository

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {

        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val database = AsteroidDatabase.getDatabase(application)
        val repository = AsteroidRepository(database)

        val factory = MainViewModel.Factory(repository)
        val mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        binding.viewModel = mainViewModel
        binding.viewModel =viewModel

        val adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            mainViewModel.displayAsteroidDetails(it)
        })

        mainViewModel.asteroids.observe(viewLifecycleOwner, { asteroids ->
            asteroids?.apply {
                adapter.submitList(this)
            }
        })

        mainViewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                mainViewModel.displayAsteroidDetailsComplete()
            }
        })

        binding.asteroidRecycler.adapter = adapter
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       viewModel.menuItemSelected(item)
        return true
    }
}