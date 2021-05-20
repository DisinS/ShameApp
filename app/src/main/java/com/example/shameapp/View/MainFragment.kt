package com.example.shameapp.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shameapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    //internal lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        //mMovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        //tTVViewModel = ViewModelProvider(this).get(TVViewModel::class.java)
        //pPersonViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSearch.setOnClickListener {
            val query = searchQueryInput.text.toString()
            searchQueryInput.text = null
            val action = MainFragmentDirections.actionMainFragmentToSearchResultsFragment(query)
            view.findNavController().navigate(action)


            //activity, R.id.shipping_host_nav
            //val navController =
                //Navigation.findNavController(getActivity(), R.id.shipping_host_nav)
            //val navController = activity?.let { it1 -> Navigation.findNavController(it1, R.id.fragmentMain) }
            //navController?.navigate(R.id.searchResultsFragment)
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}

