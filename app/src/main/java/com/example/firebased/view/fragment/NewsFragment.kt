package com.example.firebased.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebased.R
import com.example.firebased.databinding.FragmentNewsBinding
import com.example.firebased.model.Info
import com.example.firebased.recycler.NewsAdapter
import com.example.firebased.repository.NewsViewModel

class NewsFragment : Fragment(), NewsAdapter.OnNewsClick {

    lateinit var adapter: NewsAdapter
    private lateinit var binding: FragmentNewsBinding
    var currentPage = 1
    private lateinit var progressbar: ProgressBar
    private val viewModel: NewsViewModel by viewModels()

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 20
            val shouldPaginate =
                isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                        isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                    loadData(currentPage)
                    isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        val view = binding.root
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        progressbar = binding.progressBar
        viewModel.getData(currentPage).observe(viewLifecycleOwner, {
            if (it != null) {
                adapter = NewsAdapter(requireContext(), this)
                adapter.setNews(it.articles)
                val recyclerView: RecyclerView = view.findViewById(R.id.newsView)
                progressbar.visibility = View.GONE
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.addOnScrollListener(scrollListener)
            }
        })
        return view
    }

    fun loadData(pageNumber: Int) {
        currentPage=pageNumber
        currentPage++
        progressbar = binding.progressBar
        viewModel.getData(currentPage).observe(viewLifecycleOwner, {
            if (it != null) {
                adapter = NewsAdapter(requireContext(), this)
                adapter.setNews(it.articles)
                val recyclerView: RecyclerView = binding.newsView
                progressbar.visibility = View.GONE
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context)
            }
        })
    }

    override fun onItemClick(article: Info, position: Int) {
        val uri = Uri.parse(article.url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        requireActivity().startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}