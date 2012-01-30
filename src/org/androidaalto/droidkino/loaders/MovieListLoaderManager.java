/*******************************************************************************

   Copyright: 2011 Android Aalto Community

   This file is part of Droidkino.

   Droidkino is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   Droidkino is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Droidkino; if not, write to the Free Software
   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 ******************************************************************************/

package org.androidaalto.droidkino.loaders;

import org.androidaalto.droidkino.activities.MovieListFragment;
import org.androidaalto.droidkino.adapter.MovieListAdapter;
import org.androidaalto.droidkino.beans.MovieInfo;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

public class MovieListLoaderManager implements LoaderManager.LoaderCallbacks<List<MovieInfo>> {
    private static MovieListLoaderManager instance;
    private MovieListFragment movieListFragment;
    private MovieListLoader currentMovieListLoader;
    
    public static MovieListLoaderManager getInstance(MovieListFragment movieListFragment) {
        if (instance == null) {
            instance = new MovieListLoaderManager();
        }
        instance.movieListFragment = movieListFragment;
        return instance;
    }
    
    private MovieListLoaderManager() {
    }


    @Override
    public Loader<List<MovieInfo>> onCreateLoader(int id, Bundle args) {
        if (currentMovieListLoader == null) {
            currentMovieListLoader = new MovieListLoader(movieListFragment.getActivity(), null);
        }
        return currentMovieListLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<MovieInfo>> loader, List<MovieInfo> data) {
        movieListFragment.setCurrentMovieList(data);
        MovieListAdapter adapter = new MovieListAdapter(movieListFragment.getActivity(), data);
        movieListFragment.setListAdapter(adapter);
        adapter.sortByTitle();
    }

    @Override
    public void onLoaderReset(Loader<List<MovieInfo>> loader) {
        movieListFragment.setListAdapter(null);
    }

}
