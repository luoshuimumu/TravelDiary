package com.example.luoshuimumu.traveldiary.modle.frag;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luoshuimumu.traveldiary.R;
import com.example.luoshuimumu.traveldiary.modle.Act.ActCreate;
import com.example.luoshuimumu.traveldiary.modle.DB.MediaEntity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragListText.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragListText#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragListText extends AbsFragxxxList {
    ListView mListView;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragListText.
     */
    // TODO: Rename and change types and number of parameters
    public static FragListText newInstance(String param1, boolean param2) {
        FragListText fragment = new FragListText();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TYPE, param1);
        args.putBoolean(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragListText() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_llist_text, container, false);
        mListView = (ListView) view.findViewById(R.id.listview);
        mAdapter = new TextAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        initEditMode(mListView);
        return view;
    }

    private class TextAdapter extends BaseAdapter {
        public TextAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        private LayoutInflater inflater;

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.widget_frag_pic_listitem, null);
                holder = new ViewHolder();
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                holder.cb_choosed = (CheckBox) convertView.findViewById(R.id.cb_choosed);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_title.setText(getItem(position).getDate());
            holder.tv_content.setText(getItem(position).getLocation());
            holder.cb_choosed.setVisibility(View.INVISIBLE);
            if (ActCreate.FLAG_EDIT_MODE) {
                holder.cb_choosed.setVisibility(View.VISIBLE);
                holder.isChoosed = mDataSet.contains(mDataList.get(position));
                if (holder.isChoosed) holder.cb_choosed.setChecked(true);
            }else{
                holder.cb_choosed.setVisibility(View.INVISIBLE);
            }

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


    }

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
