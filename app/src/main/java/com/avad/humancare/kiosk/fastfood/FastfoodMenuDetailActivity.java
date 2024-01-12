package com.avad.humancare.kiosk.fastfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.util.Utils;
import com.avad.humancare.kiosk.model.FastfoodMenuItem;

import java.util.ArrayList;

public class FastfoodMenuDetailActivity extends FastfoodBaseActivity {

    private Context mContext;

    private LayoutInflater mInflater;
    private ViewGroup mSideMenuLayout, mDrinkMenuLayout;

    private FastfoodMenuItem mMenu = null;
    private ArrayList<FastfoodMenuItem> mSideMenuList = new ArrayList<>();
    private ArrayList<FastfoodMenuItem> mDrinkMenuList = new ArrayList<>();
    private int mSelectedSideMenuIdx = 0;
    private int mSelectedDrinkMenuIdx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastfood_menu_detail);

        mContext = this;
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Intent intent = getIntent();
        if(intent != null) {
            mMenu = intent.getParcelableExtra("menu");
            if(mMenu == null) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        setMenuData();
        initView();
    }

    private void initView() {

        // header/footer
        findViewById(R.id.btn_back_main).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        ((TextView)findViewById(R.id.btn_next_txt)).setText(R.string.order_select);

        mSideMenuLayout = findViewById(R.id.detail_side_ly);
        mDrinkMenuLayout = findViewById(R.id.detail_drink_ly);

        // side menu
        setSubMenuView(mSideMenuList, mSideMenuLayout, FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_SIDE);

        // drink menu
        setSubMenuView(mDrinkMenuList, mDrinkMenuLayout, FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DRINK);

        ImageView burgerIv = (ImageView) findViewById(R.id.menu_img);
        burgerIv.setBackgroundResource(mMenu.menuImgId);
        TextView nameTv = (TextView) findViewById(R.id.menu_name);
        nameTv.setText(mMenu.menuNameId);
        TextView priceTv = (TextView) findViewById(R.id.menu_price);
        priceTv.setText(Utils.getPriceFormat(mContext, mMenu.price, true));

    }

    private void setSubMenuView(ArrayList<FastfoodMenuItem> list, ViewGroup viewGroup, int category) {
        for(int i=0; i<list.size(); i++) {
            FastfoodMenuItem item = list.get(i);
            View v = mInflater.inflate(R.layout.fastfood_menu_detail_item, null);
            ImageView iv = (ImageView) v.findViewById(R.id.menu_img);
            TextView nameTv = (TextView) v.findViewById(R.id.menu_name);
            TextView priceTv = (TextView) v.findViewById(R.id.menu_price);
            ViewGroup imgLayout = v.findViewById(R.id.menu_ly);

            iv.setBackgroundResource(item.menuImgId);
            nameTv.setText(item.menuNameId);
            if(item.price != 0) {
                priceTv.setText("+ "+ Utils.getPriceFormat(mContext, item.price, true));
                priceTv.setVisibility(View.VISIBLE);
            } else {
                priceTv.setVisibility(View.GONE);
            }

            if(i==0) {
                imgLayout.setBackgroundResource(R.drawable.btn_sel);
            } else {
                imgLayout.setBackgroundResource(R.drawable.btn_nor);
            }

            v.setTag(i);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int idx = (Integer)view.getTag();
                    for(int j=0; j<viewGroup.getChildCount(); j++) {
                        View childView = viewGroup.getChildAt(j);

                        if(j == idx) {
                            childView.findViewById(R.id.menu_ly).setBackgroundResource(R.drawable.btn_sel);
                        } else {
                            childView.findViewById(R.id.menu_ly).setBackgroundResource(R.drawable.btn_nor);
                        }
                    }

                    switch (category) {
                        case FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_SIDE:
                            mSelectedSideMenuIdx = idx;
                            break;

                        case FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DRINK:
                            mSelectedDrinkMenuIdx = idx;
                            break;
                    }
                }
            });

            viewGroup.addView(v);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        Intent intent = null;

        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;

            case R.id.btn_next:
                int totalPrice = mSideMenuList.get(mSelectedSideMenuIdx).price + mDrinkMenuList.get(mSelectedDrinkMenuIdx).price;
                mMenu.additionalPrice = totalPrice;

                intent = new Intent();
                intent.putExtra("detail_menu", mMenu);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void setMenuData() {
        FastfoodMenuItem item = null;

        // side menu
        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_side1;
        item.menuImgId = R.drawable.img_side01;
        item.price = 0;
        item.type = FastfoodMenuItem.FastfoodMenuType.SIDE;
        mSideMenuList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_side4;
        item.menuImgId = R.drawable.img_side04;
        item.price = 1200;
        item.type = FastfoodMenuItem.FastfoodMenuType.SIDE;
        mSideMenuList.add(item);

        // drink
        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_drink1;
        item.menuImgId = R.drawable.img_drink01;
        item.price = 0;
        item.type = FastfoodMenuItem.FastfoodMenuType.DRINK;
        mDrinkMenuList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_drink2;
        item.menuImgId = R.drawable.img_drink02;
        item.price = 600;
        item.type = FastfoodMenuItem.FastfoodMenuType.DRINK;
        mDrinkMenuList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_drink3;
        item.menuImgId = R.drawable.img_drink03;
        item.price = 600;
        item.type = FastfoodMenuItem.FastfoodMenuType.DRINK;
        mDrinkMenuList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_drink4;
        item.menuImgId = R.drawable.img_drink04;
        item.price = 600;
        item.type = FastfoodMenuItem.FastfoodMenuType.DRINK;
        mDrinkMenuList.add(item);
    }
}