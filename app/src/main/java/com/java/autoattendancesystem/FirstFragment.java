package com.java.autoattendancesystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.java.autoattendancesystem.databinding.FragmentFirstBinding;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

private FragmentFirstBinding binding;
    ArrayList<Product> listProduct;
    ProductListViewAdapter productListViewAdapter;
    ListView listViewProduct;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentFirstBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        //Khoi tao ListProduct
        listProduct = new ArrayList<>();
        listProduct.add(new Product(1, "Iphone 6", 500));
        listProduct.add(new Product(2, "Iphone 7", 700));
        listProduct.add(new Product(3, "Sony Abc", 800));
        listProduct.add(new Product(4, "Samsung XYZ", 900));
        listProduct.add(new Product(5, "SP 5", 500));
        listProduct.add(new Product(6, "SP 6", 700));
        listProduct.add(new Product(7, "SP 7", 800));
        listProduct.add(new Product(8, "SP 8", 900));

        productListViewAdapter = new ProductListViewAdapter(listProduct);

        listViewProduct =  (ListView) getView().findViewById((R.id.listproduct));
        listViewProduct.setAdapter(productListViewAdapter);

    }

    public void onActivityCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class Product {
        String name;
        int price;
        int productID;

        public Product(int productID, String name, int price) {
            this.name = name;
            this.price = price;
            this.productID = productID;
        }

    }

    class ProductListViewAdapter extends BaseAdapter {

        //D??? li???u li??n k???t b???i Adapter l?? m???t m???ng c??c s???n ph???m
        final ArrayList<Product> listProduct;

        ProductListViewAdapter(ArrayList<Product> listProduct) {
            this.listProduct = listProduct;
        }

        @Override
        public int getCount() {
            //Tr??? v??? t???ng s??? ph???n t???, n?? ???????c g???i b???i ListView
            return listProduct.size();
        }

        @Override
        public Object getItem(int position) {
            //Tr??? v??? d??? li???u ??? v??? tr?? position c???a Adapter, t????ng ???ng l?? ph???n t???
            //c?? ch??? s??? position trong listProduct
            return listProduct.get(position);
        }

        @Override
        public long getItemId(int position) {
            //Tr??? v??? m???t ID c???a ph???n
            return listProduct.get(position).productID;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //convertView l?? View c???a ph???n t??? ListView, n???u convertView != null ngh??a l??
            //View n??y ???????c s??? d???ng l???i, ch??? vi???c c???p nh???t n???i dung m???i
            //N???u null c???n t???o m???i

            View viewProduct;
            if (convertView == null) {
                viewProduct = View.inflate(parent.getContext(), R.layout.product_view, null);
            } else viewProduct = convertView;

            //Bind s??? li???u ph???n t??? v??o View
            Product product = (Product) getItem(position);
            ((TextView) viewProduct.findViewById(R.id.idproduct)).setText(String.format("ID = %d", product.productID));
            ((TextView) viewProduct.findViewById(R.id.nameproduct)).setText(String.format("T??n SP : %s", product.name));
            ((TextView) viewProduct.findViewById(R.id.priceproduct)).setText(String.format("Gi?? %d", product.price));


            return viewProduct;
        }
    }

}