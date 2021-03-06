/*
 *  BugsControl
 *  Copyright (C) 2014  Jon Ander Peñalba
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

package jp.bugscontrol.github;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.util.Util.TaskListener;

public class Server extends jp.bugscontrol.general.Server {
    public Server(final String token) {
        super(GITHUB, "", GITHUB);
        password = token;
    }

    public Server(final jp.bugscontrol.db.Server server) {
        super(server);
    }

    @Override
    protected void loadProducts() {
        final Server server = this;
        final List<jp.bugscontrol.general.Product> newList = new ArrayList<jp.bugscontrol.general.Product>();
        final GithubTask task = new GithubTask(this, "/user/repos", new TaskListener() {
            @Override
            public void doInBackground(final String s) {
                try {
                    final JSONArray object = new JSONArray(s);
                    final int size = object.length();
                    for (int i = 0; i < size; ++i) {
                        final JSONObject p = object.getJSONObject(i);
                        newList.add(new Product(server, p));
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPostExecute(final String s) {
                products.clear();
                products.addAll(newList);
                productsListUpdated();
            }
        });
        task.execute();
    }
}
