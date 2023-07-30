package com.example.onlineshoppingapplicationacmegrademajorproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingapplicationacmegrademajorproject.R;
import com.example.onlineshoppingapplicationacmegrademajorproject.SharedViewModel;
import com.example.onlineshoppingapplicationacmegrademajorproject.ShoppingListItem;
import com.example.onlineshoppingapplicationacmegrademajorproject.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<ShoppingListItem> shoppingList = getShoppingList();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        adapter = new ShoppingListAdapter(shoppingList);
        recyclerView.setAdapter(adapter);

        sharedViewModel.getCartItems().observe(getViewLifecycleOwner(), new Observer<List<ShoppingListItem>>() {
            @Override
            public void onChanged(List<ShoppingListItem> shoppingList) {
                adapter.updateCartIconColors();
                adapter.notifyDataSetChanged(); // Update the adapter when cart items change
            }
        });

        return rootView;
    }

    private List<ShoppingListItem> getShoppingList() {
        List<ShoppingListItem> items = new ArrayList<>();
        items.add(new ShoppingListItem(R.drawable.shirt, "Allen Solly", "Shirt", "Rs. 2000", 1));
        items.add(new ShoppingListItem(R.drawable.cello_bottle, "CELLO", "Water bottle", "Rs. 500", 2));
        items.add(new ShoppingListItem(R.drawable.iphone_14_pro_max, "Apple", "Iphone 14 Pro Max", "Rs. 1,50,000", 3));
        items.add(new ShoppingListItem(R.drawable.realme_10_pro, "Realme", "10 Pro+ (5g)", "Rs. 25,000", 4));
        items.add(new ShoppingListItem(R.drawable.ipad_air_mi, "Apple", "Ipad Air M1", "Rs. 50,000", 5) );
        items.add(new ShoppingListItem(R.drawable.vanhusen_tshirt, "Vanheusen", "T-shirt", "Rs. 1,500", 6));
        items.add(new ShoppingListItem(R.drawable.apple_watch_series_8, "Apple", "Watch Series 8", "Rs. 50,000", 7));
        items.add(new ShoppingListItem(R.drawable.apple_airpods, "Apple", "Airpods (3rd Generation)", "Rs. 40,000", 8));
        items.add(new ShoppingListItem(R.drawable.nike_swift_3, "Nike", "Swift 3", "Rs. 5,500", 9));
        items.add(new ShoppingListItem(R.drawable.addidas_reverlrun, "Addidas", "Reverlrun", "Rs. 3,500", 10));
        items.add(new ShoppingListItem(R.drawable.nothing_phone_1, "Nothing", "Phone 1", "Rs. 29,999", 11));
        items.add(new ShoppingListItem(R.drawable.kettle, "Milton", "Kettle", "Rs. 2,500", 12));
        items.add(new ShoppingListItem(R.drawable.hp_omen_16, "Hp", "Omen 16", "Rs. 1,50,000", 13));
        items.add(new ShoppingListItem(R.drawable.alienware_laptop, "Alienware", "x15 R2 Gaming Laptop", "Rs. 4,65,500", 14));
        items.add(new ShoppingListItem(R.drawable.apple_m2_air_15, "Apple", "Air (15 Inch)", "Rs. 1,30,500", 15));

        // Add more items as needed
        return items;
    }

    private class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListViewHolder> {
        private List<ShoppingListItem> items;
        private List<Boolean> cartItemStates;

        public ShoppingListAdapter(List<ShoppingListItem> items) {
            this.items = items;
            this.cartItemStates = new ArrayList<>(Collections.nCopies(items.size(), false));
        }

        @NonNull
        @Override
        public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
            return new ShoppingListViewHolder(view);
        }

        public void updateCartIconColors() {
            for (int i = 0; i < items.size(); i++) {
                ShoppingListItem item = items.get(i);
                boolean isInCart = sharedViewModel.isInCartById(item.getItemId());
                cartItemStates.set(i, isInCart);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
            ShoppingListItem item = items.get(position);
            holder.bind(item);
            boolean isInCart = sharedViewModel.isInCartById(item.getItemId());
            holder.updateCartIconColor(isInCart);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, cartIcon;
        TextView titleTextView;
        TextView subtitleTextView;
        TextView priceTextView;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            titleTextView = itemView.findViewById(R.id.txt_company);
            subtitleTextView = itemView.findViewById(R.id.txt_product_title);
            priceTextView = itemView.findViewById(R.id.price);
            cartIcon = itemView.findViewById(R.id.cart_icon);
        }

        public void updateCartIconColor(boolean isInCart) {
            if (isInCart) {
                cartIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.green));
            } else {
                cartIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.red));
            }
        }

        public void bind(ShoppingListItem item) {
            imageView.setImageResource(item.getImage());
            titleTextView.setText(item.getTitle());
            subtitleTextView.setText(item.getSubtitle());
            priceTextView.setText(item.getPrice());

            boolean isInCart = sharedViewModel.isInCartById(item.getItemId());
            if (isInCart) {
                cartIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
            } else {
                cartIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red));
            }

            cartIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isInCart = sharedViewModel.isInCartById(item.getItemId());
                    if (!isInCart) {
                        sharedViewModel.addToCart(item);
                        cartIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green));
                        Toast.makeText(requireContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Item is already in cart", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
