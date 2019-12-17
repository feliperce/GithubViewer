package com.example.githubviewer.ui.reppr.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.githubviewer.R
import com.example.githubviewer.data.remote.response.RepPrResponse
import com.example.githubviewer.databinding.FragmentRepPrBinding
import com.example.githubviewer.databinding.FragmentRepSearchBinding
import com.example.githubviewer.extension.getErrorStringOrNull
import com.example.githubviewer.ui.rep.viewmodel.RepSearchViewModel
import com.example.githubviewer.ui.reppr.viewmodel.RepPrViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_rep_search.*
import org.jetbrains.anko.support.v4.browse
import org.koin.android.ext.android.inject

class RepPrFragment : Fragment() {

    private val viewModel: RepPrViewModel by inject()
    private var repAdapter: RepPrAdapter? = null
    private val args: RepPrFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRepPrBinding>(
            inflater, R.layout.fragment_rep_pr, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.title = args.rep

        viewModel.let {

            it.getRepPr(args.user, args.rep)

            it.errorHandlerLiveData.observe(this, Observer { ex ->
                requireContext().getErrorStringOrNull(ex)?.let { msg ->
                    Snackbar.make(rootLayout, msg, Snackbar.LENGTH_LONG).show()
                }
            })

            it.prLiveData.observe(this, Observer { prList ->
                initAdapter(prList)
            })

        }

    }

    private fun initAdapter(prList: List<RepPrResponse>) {

        val listenerLiveData = MutableLiveData<RepPrAdapter.RepPrCommand>()

        repAdapter = RepPrAdapter(listenerLiveData, prList)

        listenerLiveData.observe(this, Observer {
            when (it) {
                is RepPrAdapter.RepPrCommand.PrClick -> {
                    browse(it.url)
                }
            }
        })

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
