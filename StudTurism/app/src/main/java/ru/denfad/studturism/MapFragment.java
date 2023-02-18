package ru.denfad.studturism;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
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


public class MapFragment extends Fragment  {

    private  MapView mapview;
    private MainService service;
    private  BottomSheetBehavior bottomSheetBehavior;
    private ImageView placeImage;
    private TextView placeText;
    private TextView placeDescription;
    private LikeButton like;
    private TextView likesCount;
    private Button addPoint;
    private boolean IS_ADDING_POINT = false; //if adding point, we change state of map
    private LinearLayout addMarkLayout;
    private LinearLayout mapItemLayout;
    private Button readyButton;

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

        mapItemLayout = rootView.findViewById(R.id.map_item_layout);
        addMarkLayout = rootView.findViewById(R.id.add_mark_layout);
        addMarkLayout.setVisibility(View.GONE);
        readyButton = rootView.findViewById(R.id.button_ready);

        mapview = rootView.findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(59.948, 30.323), 12.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0.5f),
                null);
        createMarkers();


        LinearLayout llBottomSheet = (LinearLayout) rootView.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        placeImage = rootView.findViewById(R.id.place_image);
        placeImage.setClipToOutline(true);

        placeText = rootView.findViewById(R.id.place_name);
        placeDescription = rootView.findViewById(R.id.place_description);

        like = rootView.findViewById(R.id.like_button);
        likesCount = rootView.findViewById(R.id.like_count);

        addPoint = rootView.findViewById(R.id.add_point);
        addPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IS_ADDING_POINT = true;
                MapObjectCollection mapObjects = mapview.getMap().getMapObjects().addCollection();

                PlacemarkMapObject m = mapObjects.addPlacemark(mapview.getMap().getCameraPosition().getTarget());
                m.setIcon(ImageProvider.fromResource(getContext(), R.drawable.marker));

                mapview.getMap().addCameraListener(new CameraListener() {
                    @Override
                    public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
                        m.setGeometry(mapview.getMap().getCameraPosition().getTarget());
                    }
                });


                addMarkLayout.setVisibility(View.VISIBLE);
                mapItemLayout.setVisibility(View.GONE);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottomSheetBehavior.setDraggable(false);

                readyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addMarkLayout.setVisibility(View.GONE);
                        mapItemLayout.setVisibility(View.VISIBLE);
                        bottomSheetBehavior.setDraggable(true);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


                    }
                });


            }
        });
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
                    if(!IS_ADDING_POINT) {
                        mapview.getMap().move(
                                new CameraPosition(new Point(p.X, p.Y), 14.0f, 0.0f, 0.0f),
                                new Animation(Animation.Type.SMOOTH, 1),
                                null);

                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        placeImage.setImageDrawable(getResources().getDrawable(p.imageId));
                        placeText.setText(p.name);
                        placeDescription.setText(p.description);
                        likesCount.setText(String.valueOf(p.likeCount));
                        like.setOnLikeListener(new OnLikeListener() {
                            @Override
                            public void liked(LikeButton likeButton) {
                                likesCount.setText(String.valueOf(p.likeCount + 1));
                            }

                            @Override
                            public void unLiked(LikeButton likeButton) {
                                likesCount.setText(String.valueOf(p.likeCount));
                            }
                        });
                        return true;
                    }
                    return false;
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