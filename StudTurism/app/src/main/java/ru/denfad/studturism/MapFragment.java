package ru.denfad.studturism;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.ui_view.ViewProvider;

import java.util.List;

import ru.denfad.studturism.Model.UserPoint;
import ru.denfad.studturism.Sevice.MainService;


public class MapFragment extends Fragment {

    private  MapView mapview;
    private MainService service;

    public MapFragment() {
       service = MainService.getInstance();
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_map, container, false);

        mapview = rootView.findViewById(R.id.mapview);
//        mapview.getMap().move(
//                new CameraPosition(new Point(59.948, 30.323), 14.0f, 0.0f, 0.0f),
//                new Animation(Animation.Type.SMOOTH, 5),
//                null);
        createMarkers();
        return rootView;
    }

    private void createMarkers() {
        List<UserPoint> userPoints = service.getUserPoints();
        MapObjectCollection mapObjects = mapview.getMap().getMapObjects().addCollection();
        for(UserPoint p: userPoints) {
            PlacemarkMapObject mark = mapObjects.addPlacemark(new Point(p.X, p.Y), createMarker(p));
            mark.addTapListener(new MapObjectTapListener() {
                @Override
                public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
                    mapview.getMap().move(
                            new CameraPosition(new Point(p.X, p.Y), 14.0f, 0.0f, 0.0f),
                            new Animation(Animation.Type.SMOOTH, 1),
                null);

                    return true;
                }
            });
        }
    }

    private ViewProvider createMarker(UserPoint p){
        View markerView = getLayoutInflater().inflate(R.layout.map_mark, null);
        ImageView imageView = markerView.findViewById(R.id.mark_image);
        imageView.setImageDrawable(getResources().getDrawable(p.imageId));
        return new ViewProvider(markerView,true);
    }

    @Override
    public void onStop() {
        // Activity onStop call must be passed to both MapView and MapKit instance.
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        // Activity onStart call must be passed to both MapView and MapKit instance.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
    }


}