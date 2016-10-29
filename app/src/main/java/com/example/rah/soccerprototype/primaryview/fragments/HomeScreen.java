package com.example.rah.soccerprototype.primaryview.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rah.soccerprototype.R;
import com.example.rah.soccerprototype.rest.Greeting;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeScreen.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeScreen extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeScreen newInstance(String param1, String param2) {
        HomeScreen fragment = new HomeScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Log.d("soccerLogger", "mParam1: " + mParam1 );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("soccerLogger", "onCreateView" );
        int i = getArguments().getInt(ARG_PARAM1);
        String navBarName = getResources().getStringArray(R.array.navDrawerTitles)[i];
        getActivity().setTitle(navBarName);

        /**
         * Because this is a fragment with a button on it, I found that I need to set the click listener
         * via code insteaad of the convention of setting the method name in the fragment xml and
         * having a method named the same in the Activity.  The MainActivity class could pick up the
         * button click, but then you can't access the proper view, and update the textview's on this
         * fragment screen.  The best way I found was to do what's here below:
         */
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        Button refreshButton = (Button) view.findViewById(R.id.buttonRefresh);
        refreshButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("soccerprototype", "Calling Spring for a test rest service..");
                new HttpRequestTask().execute();
            }
        });

        //Return the proper view... by default you'd return an inflated view here in one line...
        // Instead, return the view that's already inflated with the refresh button onclick
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //Pretty standard setup from here:
    //http://spring.io/guides/gs/consuming-rest-android/
    //Fragments don't contain the findViewById, so you have to call getView()
    private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
        View view;

        public HttpRequestTask(){
            //Need to call getView() because this is a Fragment.. It will get the top level View.
            //Info: http://stackoverflow.com/questions/6495898/findviewbyid-in-fragment
            this.view = getView();
        }

        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Greeting greeting = restTemplate.getForObject(url, Greeting.class);
                return greeting;
            } catch (Exception e) {
                Log.e("HomeScreen", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Greeting greeting) {
            //We want to take pieces of the Greeting object and put it on the Home Screen
            //fragment view, so we need to get the View.
            this.view = getView();
            //Verify the view isn't null (it shouldn't be using getView, but was when I was using another method to get the view)
            if (view != null)
            {
                //Get the two textViews
                TextView greetingIdText = (TextView) view.findViewById(R.id.springGreetingsId);
                TextView greetingContentText = (TextView) view.findViewById(R.id.springGreetings);

                //Verify that Greeting is not null.  It is the object returned from the Spring Rest
                //test interface.
                if (greeting != null) {
                    //Set the text in both of them:
                    greetingIdText.setText(greeting.getId());
                    greetingContentText.setText(greeting.getContent());
                } else {
                    //If Greeting is null, assume the sevice is down.
                    //Set the text in both of them:
                    greetingIdText.setText("Service is down.");
                    greetingContentText.setText("Service is down.");
                }

            } else {
                Log.d("soccer", "view is null");
            }
        }
    }
}
