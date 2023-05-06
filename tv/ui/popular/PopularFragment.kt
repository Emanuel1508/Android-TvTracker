package lynx.internship.tv.ui.popular

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import lynx.internship.tv.data.api.Movie
import lynx.internship.tv.ui.commonRecycleViewElements.CommonRVAdapter
import lynx.internship.tv.databinding.FragmentPopularBinding
import lynx.internship.tv.ui.details.DetailsActivity

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PopularViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        viewModel.fetchPopular()
    }

    private fun observeData() {
        viewModel.popularListLiveData.observe(viewLifecycleOwner) { movieList ->
            binding.rvPopular.layoutManager =
                LinearLayoutManager(this@PopularFragment.context)

            binding.rvPopular.adapter = CommonRVAdapter(
                movieList,
                object : CommonRVAdapter.CommonRVAdapterClickListener {
                    override fun onItemClick(movie: Movie) {
                        val intent = Intent(
                            this@PopularFragment.context,
                            DetailsActivity::class.java
                        )
                        intent.putExtra("id", movie.id)
                        startActivity(intent)
                    }
                })
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(this@PopularFragment.context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}



