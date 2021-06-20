package com.example.firebased.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebased.R
import com.example.firebased.model.Info
import com.example.firebased.recycler.NewsAdapter
import com.example.firebased.service.news.NewsClient
import com.example.firebased.viewmodel.NewsViewModel

class NewsFragment : Fragment(), NewsAdapter.OnNewsClick {

    lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        val progressbar: ProgressBar = view.findViewById(R.id.progressBar)
        /*val news = NewsClient.getClient(requireContext()).getData("in", 1)
        news.enqueue(object : Callback<DataItem> {
            override fun onResponse(call: Call<DataItem>, response: Response<DataItem>) {
                val news = response.body()
                if (news != null) {
                }
            }
            override fun onFailure(call: Call<DataItem>, t: Throwable) {
            }
        })*/
        val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.news.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter = NewsAdapter(requireContext(), this)
                adapter.setNews(it.articles)
                val recyclerView: RecyclerView = view.findViewById(R.id.newsView)
                progressbar.visibility = View.GONE
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context)
            }
        })
        return view
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