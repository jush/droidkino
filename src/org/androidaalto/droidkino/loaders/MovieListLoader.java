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

import org.androidaalto.droidkino.DroidKinoApplicationCache;
import org.androidaalto.droidkino.beans.MovieInfo;
import org.androidaalto.droidkino.xml.ParserFactory;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class MovieListLoader extends AsyncTaskLoader<List<MovieInfo>> {
    private static final String LOG_TAG = MovieListLoader.class.getCanonicalName();
    private String areaId;

    /**
     * @param context
     */
    public MovieListLoader(Context context, String areaId) {
        super(context);
        this.areaId = areaId;
    }

    @Override
    public List<MovieInfo> loadInBackground() {
        Log.d(LOG_TAG, "Loading movie list");
        
        DroidKinoApplicationCache cache = DroidKinoApplicationCache.getInstance();
        if (cache.getMovies().size() > 0) {
            List<MovieInfo> movieList = cache.getMovies();
            return movieList;
        }

        try {
            return ParserFactory.getParser().parseMovies(areaId);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while fetching data", e);
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see android.support.v4.content.Loader#onStartLoading()
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /* (non-Javadoc)
     * @see android.support.v4.content.Loader#reset()
     */
    @Override
    public void reset() {
        super.reset();
        Log.d(LOG_TAG, "Reset called " + getId());
    }
}
