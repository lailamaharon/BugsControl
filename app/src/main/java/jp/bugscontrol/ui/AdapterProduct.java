/*
 *  BugsControl
 *  Copyright (C) 2013  Jon Ander Peñalba
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jp.bugscontrol.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jp.bugscontrol.R;
import jp.bugscontrol.general.Product;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    private final List<Product> values;
    private final ProductListFragment fragment;

    public AdapterProduct(final List<Product> values, final ProductListFragment fragment) {
        this.values = values;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int i) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product, parent, false);
        return new ViewHolder(v, fragment);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final Product p = values.get(i);
        holder.name.setText(p.getName());
        holder.description.setText(p.getDescription());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public int getProductIdFromPosition(final int position) {
        return values.get(position).getId();
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView description;

        public ViewHolder(final View v, final ProductListFragment fragment) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            description = (TextView) v.findViewById(R.id.description);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.onListItemClick(getPosition());
                }
            });
        }
    }
}
