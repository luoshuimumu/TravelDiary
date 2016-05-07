package com.example.luoshuimumu.traveldiary.modle.frag;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luoshuimumu.traveldiary.LocationApplication;
import com.example.luoshuimumu.traveldiary.R;
import com.example.luoshuimumu.traveldiary.modle.DB.MediaEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AbsFragxxxList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AbsFragxxxList#newInstance} factory method to
 * create an instance of this fragment.
 */
public abstract class AbsFragxxxList extends Fragment {
    //数据初始化模块
    private boolean FLAG_DATA_INIT_COMPLETE = false;
    private Dialog mWaitDialog;

    //通用控件
    ListView mAbsListView;
    //通用适配器
    BaseAdapter mAdapter;
    //通用数据项
    List<MediaEntity> mDataList;
    //需要每个fragment单独记录已经被选定的媒体项
    //有序 不允许重复
    Set mDataSet = new TreeSet();

    Handler mUIHandler;

    //与activity交互
//    abstract void refreshUI();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    protected static final String ARG_PARAM_TYPE = MediaEntity.COLUMN_NAME_TYPE;
    protected static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //type用于数据库查询函数时识别查询的媒体种类
    //预设为数据表最长五十个
    protected String mParam1;
    protected boolean mParam2;

    /**
     * 需要查date（可以生成title）uri location 三条数据
     *
     * @param type
     */
    private void initDataList(String type) throws NullPointerException {
//        String SQL_TEST = "select date,location,uri from media;";
//        Cursor cursor_test = LocationApplication.dbHelper.getReadableDatabase()
//                .rawQuery(SQL_TEST, null);

        String SQL = "select date,location,uri,_id from media where type=\"" + type + "\";";

        mDataList = new ArrayList<>();
//        LocationApplication.dbHelper.getReadableDatabase().execSQL(SQL);
        try {
            Cursor cursor = LocationApplication.dbHelper.getReadableDatabase()
                    .rawQuery(SQL, null);
            if (cursor == null || cursor.getCount() == 0) {
                //错误处理
                return;
            }
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                MediaEntity entity = new MediaEntity();
//                for (int i = 0; i < cursor.getColumnCount(); i++) {
//                    Log.e(type, "cursor.getString:" + i + "  " + cursor.getString(i));
//                }
                entity.setDate(cursor.getString(cursor.getColumnIndex(MediaEntity.COLUMN_NAME_DATE)));
                entity.setLocation(cursor.getString(cursor.getColumnIndex(MediaEntity.COLUMN_NAME_LCOATION)));
                entity.setUri(cursor.getString(cursor.getColumnIndex(MediaEntity.COLUMN_NAME_URI)));
                entity.setId(cursor.getString(cursor.getColumnIndex(MediaEntity.COLUMN_NAME_ID)));

                mDataList.add(entity);
            }

            cursor.close();
            LocationApplication.dbHelper.close();
            FLAG_DATA_INIT_COMPLETE = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
//    Cursor cursor = db.rawQuery(“select * from person”, null);
//
//    while (cursor.moveToNext()) {
//
//        int personid = cursor.getInt(0); //获取第一列的值,第一列的索引从0开始
//
//        String name = cursor.getString(1);//获取第二列的值
//
//        int age = cursor.getInt(2);//获取第三列的值
//
//    }
//
//    cursor.close();
//
//    db.close();

    protected OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AbsFragxxxList.
     */
    // TODO: Rename and change types and number of parameters
    public static AbsFragxxxList newInstance(String param1, String param2) {
        //在这里初始化类头部声明的成员


//        AbsFragxxxList fragment = new AbsFragxxxList();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
        return null;
    }

    public AbsFragxxxList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM_TYPE);
            mParam2 = getArguments().getBoolean(ARG_PARAM2);
        }
        Log.e("mParam2", String.valueOf(mParam2));
//        waitDialogShow();
        initDataList(mParam1);


        //开启异步进程等待数据库初始化完成 取消等待dialog
        //
    }

    /**
     * 这个函数在以edit mode启动fragment的时候启用
     *
     * @param listView 不同fragment的listview
     */
    protected void initEditMode(ListView listView) {
        this.mAbsListView = listView;
        mAbsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mParam2) return;
                ViewHolder holder = (ViewHolder) view.getTag();
                MediaEntity entity = mDataList.get(position);
                //若未被选择即加入
                if (!holder.isChoosed) {

                    onButtonPressed(entity, "add");
                    //同时更新本地列表的状态
                    mDataSet.add(entity);
                } else {
                    onButtonPressed(entity, "delete");
                    mDataSet.remove(entity);
                }
                //切换选择状态
                holder.cb_choosed.toggle();

            }
        });
    }

    protected class ViewHolder {
        TextView tv_title;
        TextView tv_content;
        ImageView iv_thumbnail;
        CheckBox cb_choosed;
        boolean isChoosed = false;
    }

    /**
     * 开启等待dialog
     */
    private void waitDialogShow() {
        if (mWaitDialog == null) {
            //在content里设置一个progressDialog
            mWaitDialog = new ProgressDialog(getActivity());
        }
        if (!FLAG_DATA_INIT_COMPLETE) {
            mWaitDialog.show();
        }
    }

    /**
     * 关闭等待dialog
     */
    private void waitDialogDismiss() {
        if (mWaitDialog != null && mWaitDialog.isShowing()) {
            mWaitDialog.show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_abs_fragxxx_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(MediaEntity entity, String mode) {
        if (mListener != null) {
            mListener.onFragmentInteraction(entity, mode);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(MediaEntity entity, String mode);
    }

//    abstract public void refreshLocation();


    @Override
    public void onResume() {
        super.onResume();
//        waitDialogShow();
    }

    @Override
    public void onPause() {
        super.onPause();
//        waitDialogDismiss();
    }
}
