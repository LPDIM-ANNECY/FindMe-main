package fr.test200.findme.onBoarding

import fr.test200.findme.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import fr.test200.findme.databinding.OnBoardingFragmentBinding


class OnBoardingViewFragment : Fragment() {
    class PageFragment : Fragment() {


        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            // 3 - Get layout of PageFragment
            val result: View = inflater.inflate(R.layout.on_boarding_view_fragment, container, false)

            // 4 - Get widgets from layout and serialise it
            val image = result.findViewById(R.id.intro_img) as ImageView
            val textTitre = result.findViewById(R.id.intro_title) as TextView
            val textDesc = result.findViewById(R.id.intro_description) as TextView

            // 5 - Get data from Bundle (created in method newInstance)
            val position = requireArguments().getInt(KEY_POSITION, -1)


            // 6 - Update widgets with it


            if (position == 0){
                image.setBackgroundResource(R.drawable.onboarding_2)
                textTitre.text = getString(R.string.onboarding_title_1)
                textDesc.text = getString(R.string.onboarding_desc_1)
            }
            else if (position == 1){
                image.setBackgroundResource(R.drawable.onboarding_1)
                textTitre.text = getString(R.string.onboarding_title_2)
                textDesc.text = getString(R.string.onboarding_desc_2)
            }
            else if (position == 2){
                image.setBackgroundResource(R.drawable.onboarding_3)
                textTitre.text = getString(R.string.onboarding_title_3)
                textDesc.text = getString(R.string.onboarding_desc_3)
            }


            Log.e(javaClass.simpleName, "onCreateView called for fragment number $position")
            return result
        }

        companion object {
            // 1 - Create keys for our Bundle
            private const val KEY_POSITION = "position"


            // 2 - Method that will create a new instance of PageFragment, and add data to its bundle.
            fun newInstance(position: Int): PageFragment {
1
                // 2.1 Create new fragment
                val frag = PageFragment()

                // 2.2 Create bundle and add it some data
                val args = Bundle()
                args.putInt(KEY_POSITION, position)
                frag.arguments = args
                return frag
            }
        }
    }
}