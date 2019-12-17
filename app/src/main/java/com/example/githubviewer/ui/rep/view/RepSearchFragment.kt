package com.example.githubviewer.ui.rep.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.githubviewer.R
import com.example.githubviewer.databinding.FragmentRepSearchBinding
import com.example.githubviewer.extension.getErrorStringOrNull
import com.example.githubviewer.ui.rep.viewmodel.RepSearchViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_rep_search.*
import org.koin.android.ext.android.inject

class RepSearchFragment : Fragment() {

    private val viewModel: RepSearchViewModel by inject()
    private var repAdapter: RepAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRepSearchBinding>(
            inflater, R.layout.fragment_rep_search, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewModel.let {
            it.initializedPagedListBuilder()
            it.repLiveData.observe(this, Observer { repoList ->
                repAdapter?.submitList(repoList)
            })
            it.errorHandlerLiveData.observe(this, Observer { ex ->
                requireContext().getErrorStringOrNull(ex)?.let { msg ->
                    Snackbar.make(rootLayout, msg, Snackbar.LENGTH_LONG).show()
                }
            })

            refreshFab.setOnClickListener { view ->
                initAdapter()
                it.initializedPagedListBuilder()
                it.repLiveData.observe(this, Observer { repoList ->
                    repAdapter?.submitList(repoList)
                })
            }
        }

    }

    private fun initAdapter() {

        val listenerLiveData = MutableLiveData<RepAdapter.RepCommand>()

        listenerLiveData.observe(this, Observer {
            when (it) {
                is RepAdapter.RepCommand.RepClick -> {
                    RepSearchFragmentDirections
                    val action = RepSearchFragmentDirections
                        .actionRepoSearchFragmentToRepPrFragment(it.user, it.rep)
                    findNavController().navigate(action)
                }
            }
        })

        repAdapter = RepAdapter(listenerLiveData)

        repoRecyclerView.apply {
            this.adapter = repAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }
    }
}
