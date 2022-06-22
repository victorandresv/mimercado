package cl.mi.mercado.pages;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import cl.mi.mercado.R;
import cl.mi.mercado.adapters.HomeFragmentStateAdapter;
import cl.mi.mercado.fragments.ProductsFragment;
import cl.mi.mercado.fragments.SalesFragment;
import cl.mi.mercado.helpers.DialogsHelper;
import cl.mi.mercado.helpers.SessionHelper;
import cl.mi.mercado.interfaces.ProductCallback;
import cl.mi.mercado.models.ProductModel;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DialogsHelper.ProductCreate(this, new ProductCallback() {
            @Override
            public void Add(ProductModel data) {
                Log.e("ERR", data.toString());
            }
        });

        this.setTitle(SessionHelper.getData(this, "Storename"));
        getSupportActionBar().setSubtitle(getResources().getString(R.string.home));

        HomeFragmentStateAdapter hfsa = new HomeFragmentStateAdapter(getSupportFragmentManager(),getLifecycle());
        hfsa.addFragment(new SalesFragment());
        hfsa.addFragment(new ProductsFragment());
        ViewPager2 viewPager2 = findViewById(R.id.viewpager);
        viewPager2.setAdapter(hfsa);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    switch (position){
                        case 0:
                            tab.setText(getResources().getText(R.string.sales));
                            break;
                        case 1:
                            tab.setText(getResources().getText(R.string.products));
                            break;
                    }
                }
        ).attach();

    }
}