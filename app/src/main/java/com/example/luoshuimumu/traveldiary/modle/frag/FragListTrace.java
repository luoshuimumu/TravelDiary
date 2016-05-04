package com.example.luoshuimumu.traveldiary.modle.frag;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luoshuimumu.traveldiary.R;
import com.example.luoshuimumu.traveldiary.modle.DB.MediaEntity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragListTrace.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragListTrace#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragListTrace extends AbsFragxxxList {

    ListView mListView;
    private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static FragListTrace newInstance(String param1, String param2) {
        FragListTrace fragment = new FragListTrace();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TYPE, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragListTrace() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_list_trace, container, false);
        mListView = (ListView) view.findViewById(R.id.listview);
        mAdapter = new PicAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        return view;
    }

    private class PicAdapter extends BaseAdapter {
        public PicAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        private LayoutInflater inflater;

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.widget_frag_pic_listitem, null);
                holder = new Holder();
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.tv_title.setText(getItem(position).getDate());
            holder.tv_content.setText(getItem(position).getLocation());

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public MediaEntity getItem(int position) {
            return mDataList.get(position);
        }

        private class Holder {
            TextView tv_title;
            TextView tv_content;
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
