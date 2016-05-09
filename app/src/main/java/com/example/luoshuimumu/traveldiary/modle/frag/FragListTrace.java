package com.example.luoshuimumu.traveldiary.modle.frag;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;

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
    public static FragListTrace newInstance(String param1, boolean param2) {
        FragListTrace fragment = new FragListTrace();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TYPE, param1);
        args.putBoolean(ARG_PARAM2, param2);
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
        super.onCreateView(inflater,container,savedInstanceState);
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
            holder.id = getItem(position).getId();
            if (ActCreate.FLAG_EDIT_MODE) {
                holder.cb_choosed.setVisibility(View.VISIBLE);
                holder.isChoosed = mEditStateDataList.contains(holder.id);
                if (holder.isChoosed) holder.cb_choosed.setChecked(true);
            } else {
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

}
