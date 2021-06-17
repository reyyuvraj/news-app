package com.example.firebased.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebased.R
import com.example.firebased.data.DataItem
import com.example.firebased.data.Info
import com.example.firebased.recycler.NewsAdapter
import com.example.firebased.service.news.NewsClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val news = NewsClient.newsCall.getData("in", 1)
        news.enqueue(object : Callback<DataItem> {
            override fun onResponse(call: Call<DataItem>, response: Response<DataItem>) {
                val news = response.body()
                if (news != null) {
                    adapter = NewsAdapter(
                        context!!, news.articles,
                        this@NewsFragment
                    )
                    val recyclerView: RecyclerView = view.findViewById(R.id.newsView)
                    progressbar.visibility = View.GONE
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(context)
                }
            }

            override fun onFailure(call: Call<DataItem>, t: Throwable) {
                Toast.makeText(requireActivity(),"Failed to fetch news.",Toast.LENGTH_SHORT).show()
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