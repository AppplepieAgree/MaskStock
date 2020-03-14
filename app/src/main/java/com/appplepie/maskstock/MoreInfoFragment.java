package com.appplepie.maskstock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_more_info, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.web_view_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<InfoUrl> infoUrls = new ArrayList<>();
        infoUrls.add(new InfoUrl("식약처 :: 마스크 사용 권고", "https://www.mfds.go.kr/brd/m_99/view.do?seq=43991"));
        infoUrls.add(new InfoUrl("식약처 :: 마스크 사용 권고사항", "http://www.blog.naver.com/kfdazzang/221837044802"));
        infoUrls.add(new InfoUrl("식약처 :: 공적마스크 구입 안내", "http://www.blog.naver.com/kfdazzang/221844817502"));
        infoUrls.add(new InfoUrl("식약처 :: 공적마스크 Q&A", "https://www.mfds.go.kr/brd/m_659/list.do"));
        recyclerView.setAdapter(new ParentAdapter(infoUrls, getActivity()));





        // Inflate the layout for this fragment
        return view;
    }
}
