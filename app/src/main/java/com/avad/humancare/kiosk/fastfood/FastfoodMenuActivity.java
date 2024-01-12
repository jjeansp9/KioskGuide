package com.avad.humancare.kiosk.fastfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.adapter.FastfoodGridAdapter;
import com.avad.humancare.kiosk.adapter.FastfoodOrderListAdapter;
import com.avad.humancare.kiosk.util.Utils;
import com.avad.humancare.kiosk.model.FastfoodMenuItem;
import com.avad.humancare.kiosk.view.FastfoodMenuItemGridView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.RecyclerView;

public class FastfoodMenuActivity extends FastfoodBaseActivity {

    private String TAG = FastfoodMenuActivity.class.getSimpleName();

    private final int TABLE_CELL_COUNT_PHONE = 2;
    private final int TABLE_CELL_COUNT_TABLET = 3;

    private Context mContext;

    private LayoutInflater mInflater;
    private ViewGroup mLeftMenuLayout, mHScrollviewLayout;
    private FastfoodMenuItemGridView mGridView;
    private RecyclerView mOrderListView;
    private TextView mOrderCountTv, mTotalPriceTv;
    private Button mBackMainBtn;

    private FastfoodOrderListAdapter mOrderListAdapter;

    private int[] menuTitleNameItemsArr = {R.string.fastfood_menu_title1, R.string.fastfood_menu_title2, R.string.fastfood_menu_title3, R.string.fastfood_menu_title4, R.string.fastfood_menu_title5};
    private int[] menuTitleImgItemsArr = {R.drawable.img_burger01, R.drawable.img_set03, R.drawable.img_side04, R.drawable.img_drink05, R.drawable.img_icecream03};

    private ArrayList<FastfoodMenuItem> mMenuItemList = new ArrayList<>();  // right view menu
    private ArrayList<FastfoodMenuItem> mOrderItemList = new ArrayList<>();
    private HashMap<Integer, Integer> mOrderMenuTypeMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastfood_menu);

        mContext = this;
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setMenuData();
        initView();
    }

    private void initView() {

        // header/footer
        ((TextView)findViewById(R.id.btn_back_txt)).setText(R.string.order_cancel);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        mBackMainBtn = (Button) findViewById(R.id.btn_back_main);
        mBackMainBtn.setOnClickListener(this);

        mLeftMenuLayout = findViewById(R.id.left_menu_ly);
        mOrderListView = findViewById(R.id.order_listview);

        mGridView = (FastfoodMenuItemGridView) findViewById(R.id.gridView);
        mGridView.setExpanded(true);

        if (Utils.isScreenWidthGreaterThan600dp(mContext)) mGridView.setNumColumns(TABLE_CELL_COUNT_TABLET); // 실행중인 앱이 태블릿인 경우
        else mGridView.setNumColumns(TABLE_CELL_COUNT_PHONE); // 실행중인 앱이 폰인 경우

        mOrderCountTv = (TextView) findViewById(R.id.txt_order_count);
        mOrderCountTv.setText(getString(R.string.fastfood_order_count, mOrderItemList.size()));
        mTotalPriceTv = (TextView) findViewById(R.id.txt_total_price);
        mTotalPriceTv.setText(getString(R.string.fastfood_total_price, "0"));

        // left menu
        setLeftMenuView();

        // right menu
        setRightMenuView(FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE);

        mOrderListAdapter = new FastfoodOrderListAdapter(this, mOrderItemList);
        mOrderListView.setAdapter(mOrderListAdapter);

    }

    private ArrayList<FastfoodMenuItem> getMenuItemListByCategory(int category) {
        // 메뉴 타입별 메뉴아이템리스트 가져오기
        ArrayList<FastfoodMenuItem> list = new ArrayList<>();

        for(int i=0; i<mMenuItemList.size(); i++) {
            if(mMenuItemList.get(i).category == category) {
                list.add(mMenuItemList.get(i));
            }
        }

        return list;
    }

    private void setLeftMenuView() {

        for(int i=0; i<menuTitleNameItemsArr.length; i++) {

            View v = mInflater.inflate(R.layout.fastfood_menu_left_item, null);
            ImageView iv = (ImageView) v.findViewById(R.id.menu_img);
            TextView tv = (TextView) v.findViewById(R.id.menu_txt);
            ViewGroup ly = v.findViewById(R.id.menu_ly);

            iv.setBackgroundResource(menuTitleImgItemsArr[i]);
            tv.setText(menuTitleNameItemsArr[i]);

            if(i==0) {
                ly.setBackgroundResource(R.drawable.btn_sel);
            } else {
                ly.setBackgroundResource(R.drawable.btn_nor);
            }

            v.setTag(i);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int idx = (Integer)view.getTag();
                    for(int j=0; j<mLeftMenuLayout.getChildCount(); j++) {
                        View childView = mLeftMenuLayout.getChildAt(j);

                        if(j == idx) {
                            childView.findViewById(R.id.menu_ly).setBackgroundResource(R.drawable.btn_sel);
                        } else {
                            childView.findViewById(R.id.menu_ly).setBackgroundResource(R.drawable.btn_nor);
                        }
                    }

                    int menuType = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE;
                    switch (idx) {
                        case 0 :
                            menuType = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE;
                            //menuType = MenuItem.FASTFOOD_MENU_TYPE_BURGER;
                            break;

                        case 1 :
                            menuType = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_BURGERSET;
                            break;

                        case 2 :
                            menuType = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_SIDE;
                            break;

                        case 3 :
                            menuType = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DRINK;
                            break;

                        case 4 :
                            menuType = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DESSERT;
                            break;
                    }

                    setRightMenuView(menuType);
                }
            });

            mLeftMenuLayout.addView(v);
        }

        mLeftMenuLayout.invalidate();
    }

    private void setRightMenuView(int category) {

        ArrayList<FastfoodMenuItem> menuList = getMenuItemListByCategory(category);

        mGridView.setAdapter(new FastfoodGridAdapter(mContext, menuList));
    }

    public void updateOrderListView(FastfoodMenuItem item) {
        //Log.d(TAG, "updateOrderListView() : " + getString(item.menuNameId));

        if(item.type == FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_BURGERSET) {
            // 버거 세트인 경우 주문내역 리스트에 아이템 하나씩 추가
            item.count = 1;
            mOrderItemList.add(item);
        } else {
            // 단품인 경우 기존 추가된 메뉴에서 아이템 카운트 증가, 기존에 추가된 메뉴가 없는 경우 새로운 메뉴 아이템 추가
            boolean isExist = false;
            for(int i=0; i<mOrderItemList.size(); i++) {
                FastfoodMenuItem menu = mOrderItemList.get(i);
                if(menu.menuNameId == item.menuNameId) {
                    isExist = true;
                    menu.count++;
                    break;
                }
            }

            if(isExist == false) {
                item.count = 1;
                mOrderItemList.add(item);
            }
        }

        updateOrderDetailView();
    }

    public void updateOrderDetailView() {
        mOrderListAdapter.notifyDataSetChanged();

        int totalPrice = 0;
        int totalCount = 0;

        for(int i=0; i<mOrderItemList.size(); i++) {
            FastfoodMenuItem item = mOrderItemList.get(i);
            totalPrice += (item.price + item.additionalPrice) * item.count;
            totalCount += item.count;
        }

        mOrderCountTv.setText(getString(R.string.fastfood_order_count, totalCount));
        mTotalPriceTv.setText(getString(R.string.fastfood_total_price, Utils.getPriceFormat(mContext, totalPrice, false)));
    }

    public void selectSideMenuOfSetMenu(FastfoodMenuItem item) {
        // 세트메뉴인 경우 사이드메뉴 선택 화면으로 이동
        Intent intent = new Intent(this, FastfoodMenuDetailActivity.class);
        intent.putExtra("menu", item);
        mLauncher.launch(intent);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = null;

        switch (view.getId()) {
            case R.id.btn_back :
                mBackMainBtn.performClick(); // 주문취소 - 키오스크 종료?? 아님 메인으로???
                break;

            case R.id.btn_next:
                if(mOrderItemList != null && mOrderItemList.size() > 0) {
                    startActivity(new Intent(this, FastfoodPaymentSelectActivity.class));
                }
                break;

        }
    }

    private void setMenuData() {
        FastfoodMenuItem item = null;

        // favorite
        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burgerset1;
        item.menuImgId = R.drawable.img_set01;
        item.price = 7500;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGERSET;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burgerset2;
        item.menuImgId = R.drawable.img_set02;
        item.price = 6500;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGERSET;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burgerset3;
        item.menuImgId = R.drawable.img_set03;
        item.price = 6200;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGERSET;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burger1;
        item.menuImgId = R.drawable.img_burger01;
        item.price = 5500;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGER;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burger2;
        item.menuImgId = R.drawable.img_burger02;
        item.price = 4500;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGER;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burger3;
        item.menuImgId = R.drawable.img_burger03;
        item.price = 4300;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGER;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_side1;
        item.menuImgId = R.drawable.img_side01;
        item.price = 1500;
        item.type = FastfoodMenuItem.FastfoodMenuType.SIDE;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_drink1;
        item.menuImgId = R.drawable.img_drink01;
        item.price = 2000;
        item.type = FastfoodMenuItem.FastfoodMenuType.DRINK;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_dessert3;
        item.menuImgId = R.drawable.img_icecream03;
        item.price = 2500;
        item.type = FastfoodMenuItem.FastfoodMenuType.DESSERT;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_FAVORITE;
        mMenuItemList.add(item);

        // burger set
        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burger1;
        item.menuImgId = R.drawable.img_burger01;
        item.price = 5500;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGER;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_BURGERSET;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burger2;
        item.menuImgId = R.drawable.img_burger02;
        item.price = 4500;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGER;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_BURGERSET;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burger3;
        item.menuImgId = R.drawable.img_burger03;
        item.price = 4300;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGER;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_BURGERSET;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burgerset1;
        item.menuImgId = R.drawable.img_set01;
        item.price = 7500;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGERSET;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_BURGERSET;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burgerset2;
        item.menuImgId = R.drawable.img_set02;
        item.price = 6500;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGERSET;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_BURGERSET;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_burgerset3;
        item.menuImgId = R.drawable.img_set03;
        item.price = 6300;
        item.type = FastfoodMenuItem.FastfoodMenuType.BURGERSET;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_BURGERSET;
        mMenuItemList.add(item);

        // side
        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_side1;
        item.menuImgId = R.drawable.img_side01;
        item.price = 1500;
        item.type = FastfoodMenuItem.FastfoodMenuType.SIDE;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_SIDE;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_side2;
        item.menuImgId = R.drawable.img_side02;
        item.price = 3000;
        item.type = FastfoodMenuItem.FastfoodMenuType.SIDE;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_SIDE;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_side3;
        item.menuImgId = R.drawable.img_side03;
        item.price = 3500;
        item.type = FastfoodMenuItem.FastfoodMenuType.SIDE;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_SIDE;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_side4;
        item.menuImgId = R.drawable.img_side04;
        item.price = 3000;
        item.type = FastfoodMenuItem.FastfoodMenuType.SIDE;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_SIDE;
        mMenuItemList.add(item);

        // drink
        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_drink1;
        item.menuImgId = R.drawable.img_drink01;
        item.price = 2000;
        item.type = FastfoodMenuItem.FastfoodMenuType.DRINK;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DRINK;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_drink2;
        item.menuImgId = R.drawable.img_drink02;
        item.price = 3000;
        item.type = FastfoodMenuItem.FastfoodMenuType.DRINK;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DRINK;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_drink3;
        item.menuImgId = R.drawable.img_drink03;
        item.price = 3000;
        item.type = FastfoodMenuItem.FastfoodMenuType.DRINK;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DRINK;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_drink4;
        item.menuImgId = R.drawable.img_drink04;
        item.price = 3000;
        item.type = FastfoodMenuItem.FastfoodMenuType.DRINK;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DRINK;
        mMenuItemList.add(item);

        // dessert
        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_dessert1;
        item.menuImgId = R.drawable.img_icecream01;
        item.price = 2500;
        item.type = FastfoodMenuItem.FastfoodMenuType.DESSERT;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DESSERT;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_dessert2;
        item.menuImgId = R.drawable.img_icecream02;
        item.price = 2500;
        item.type = FastfoodMenuItem.FastfoodMenuType.DESSERT;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DESSERT;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_dessert3;
        item.menuImgId = R.drawable.img_icecream03;
        item.price = 2500;
        item.type = FastfoodMenuItem.FastfoodMenuType.DESSERT;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DESSERT;
        mMenuItemList.add(item);

        item = new FastfoodMenuItem();
        item.menuNameId = R.string.fastfood_dessert4;
        item.menuImgId = R.drawable.img_icecream04;
        item.price = 2500;
        item.type = FastfoodMenuItem.FastfoodMenuType.DESSERT;
        item.category = FastfoodMenuItem.FASTFOOD_MENU_CATEGORY_DESSERT;
        mMenuItemList.add(item);

    }

    private ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();

                if(intent.hasExtra("detail_menu")) {
                    FastfoodMenuItem item = intent.getParcelableExtra("detail_menu");
                    //Log.d(TAG, "registerForActivityResult() : " + item.price + " / " + item.additionalPrice);
                    updateOrderListView(item);
                }


            }
        }
    });
}