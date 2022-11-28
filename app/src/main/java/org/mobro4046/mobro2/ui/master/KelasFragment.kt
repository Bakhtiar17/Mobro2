package org.mobro4046.mobro2.ui.master

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import org.mobro4046.mobro2.R
import org.mobro4046.mobro2.databinding.FragmentKelasBinding

class KelasFragment : Fragment() {
    private lateinit var binding: FragmentKelasBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentKelasBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val kelas = resources.getStringArray(R.array.kelas)
        binding.listView.adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_list_item_1, android.R.id.text1, kelas)
        binding.listView.setOnItemClickListener { _, _, pos, _ ->
            findNavController().navigate(
                KelasFragmentDirections.actionKelasFragmentToMainFragment(kelas[pos])
            )
        }
    }
}
