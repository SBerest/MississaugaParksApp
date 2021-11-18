package com.example.ggr494;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.osmdroid.util.GeoPoint;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private static final String TAG = "FILTER";
    private static final int NUMCORNERS = 4;

    ArrayList<Park> allParks;
    ArrayList<Park> parksToDraw;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private Menu menu;
    ImageButton baseballButton;
    ImageButton basketballButton;
    ImageButton bocceButton;
    ImageButton cricketButton;
    ImageButton rinkButton;
    ImageButton playsiteButton;
    ImageButton soccerButton;
    ImageButton softballButton;
    ImageButton spraypadButton;
    ImageButton tennisButton;

    ArrayList <ImageButton> aroundButtons = new ArrayList<>();
    ArrayList<Boolean> aroundStates = new ArrayList<>();

    ArrayList <ImageButton> partButtons = new ArrayList<>();
    ArrayList<Boolean> partStates = new ArrayList<>();

    Button filterButton;

    ArrayList<Location> userLocations;
    ArrayList<ImageButton> buttons = new ArrayList<>();

    ArrayList<Integer> amenityFilter;
    Hashtable<Integer, Pair<Integer, ImageButton>> buttonAmenityDictionary = new Hashtable<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        //get parks
        allParks = ((ManagingClass)getApplicationContext()).allParks;
        parksToDraw = new ArrayList<>(allParks);
        userLocations = ((ManagingClass) getApplicationContext()).userLocations;

        //Declare Buttons
        baseballButton = findViewById(R.id.filterBaseballButton);
        baseballButton.setOnClickListener(this);
        baseballButton.setOnLongClickListener(this);
        baseballButton.setBackgroundColor(Color.TRANSPARENT);
        buttons.add(baseballButton);

        basketballButton = findViewById(R.id.filterBasketballButton);
        basketballButton.setOnClickListener(this);
        basketballButton.setOnLongClickListener(this);
        basketballButton.setBackgroundColor(Color.TRANSPARENT);
        buttons.add(basketballButton);

        bocceButton = findViewById(R.id.filterBocceButton);
        bocceButton.setOnClickListener(this);
        bocceButton.setOnLongClickListener(this);
        bocceButton.setBackgroundColor(Color.TRANSPARENT);
        buttons.add(bocceButton);

        cricketButton = findViewById(R.id.filterCricketButton);
        cricketButton.setOnClickListener(this);
        cricketButton.setOnLongClickListener(this);
        cricketButton.setBackgroundColor(Color.TRANSPARENT);
        buttons.add(cricketButton);

        rinkButton = findViewById(R.id.filterRinkButton);
        rinkButton.setOnClickListener(this);
        rinkButton.setOnLongClickListener(this);
        rinkButton.setBackgroundColor(Color.TRANSPARENT);
        buttons.add(rinkButton);


        playsiteButton = findViewById(R.id.filterPlaysiteButton);
        playsiteButton.setOnClickListener(this);
        playsiteButton.setOnLongClickListener(this);
        playsiteButton.setBackgroundColor(Color.TRANSPARENT);
        buttons.add(playsiteButton);

        soccerButton = findViewById(R.id.filterSoccerButton);
        soccerButton.setOnClickListener(this);
        soccerButton.setOnLongClickListener(this);
        soccerButton.setBackgroundColor(Color.TRANSPARENT);
        buttons.add(soccerButton);

        softballButton = findViewById(R.id.filterSoftballButton);
        softballButton.setOnClickListener(this);
        softballButton.setOnLongClickListener(this);
        softballButton.setBackgroundColor(Color.TRANSPARENT);
        buttons.add(softballButton);

        spraypadButton = findViewById(R.id.filterSpraypadButton);
        spraypadButton.setOnClickListener(this);
        spraypadButton.setOnLongClickListener(this);
        spraypadButton.setBackgroundColor(Color.TRANSPARENT);
        buttons.add(spraypadButton);

        tennisButton = findViewById(R.id.filterTennisButton);
        tennisButton.setOnClickListener(this);
        tennisButton.setOnLongClickListener(this);
        tennisButton.setBackgroundColor(Color.TRANSPARENT);
        buttons.add(tennisButton);

        filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(this);

        //reset amenity filter
        amenityFilter = ((ManagingClass) getApplicationContext()).amenityFilter;
        Log.d(TAG,"AmenityFilter"+amenityFilter);
        if(amenityFilter.size() == 0) {
            for (int i = 0; i < 10; i++) {
                amenityFilter.add(0);
            }
        }
        else{
            colorButtons();
        }

        //set up and declare around and part buttons
        if(userLocations != null && userLocations.size() != 0) {
            makeAroundButtons();
            makePartButtons();
            assignAddresses();
        }

        fillHashTable();
    }

    private void colorButtons() {
        for(int i = 0; i < buttons.size(); i++) {
            if (amenityFilter.get(i) > 0) {
                buttons.get(i).setBackgroundColor(getResources().getColor(R.color.purple_700));
            }
        }
    }

    private void makeAroundButtons() {
        switch(userLocations.size()) {
            case 4:
                ImageButton tempButton3 = findViewById(R.id.aroundButton3);
                ImageView tempButton3Overlay = findViewById(R.id.notAroundButton3);
                tempButton3Overlay.setVisibility(View.INVISIBLE);
                tempButton3.setOnClickListener(this);
                tempButton3.setOnLongClickListener(this);
                aroundButtons.add(0,tempButton3);
                aroundStates.add(false);
            case 3:
                ImageButton tempButton2 = findViewById(R.id.aroundButton2);
                ImageView tempButton2Overlay = findViewById(R.id.notAroundButton2);
                tempButton2Overlay.setVisibility(View.INVISIBLE);
                tempButton2.setOnClickListener(this);
                tempButton2.setOnLongClickListener(this);
                aroundButtons.add(0,tempButton2);
                aroundStates.add(false);
            case 2:
                ImageButton tempButton1 = findViewById(R.id.aroundButton1);
                ImageView tempButton1Overlay = findViewById(R.id.notAroundButton1);
                tempButton1Overlay.setVisibility(View.INVISIBLE);
                tempButton1.setOnClickListener(this);
                tempButton1.setOnLongClickListener(this);
                aroundButtons.add(0,tempButton1);
                aroundStates.add(false);
            case 1:
                ImageButton tempButton0 = findViewById(R.id.aroundButton0);
                ImageView tempButton0Overlay = findViewById(R.id.notAroundButton0);
                tempButton0Overlay.setVisibility(View.INVISIBLE);
                tempButton0.setOnClickListener(this);
                tempButton0.setOnLongClickListener(this);
                aroundButtons.add(0,tempButton0);
                aroundStates.add(false);
            default:
                break;
        }
    }

    private void makePartButtons() {
        switch(userLocations.size()) {
            case 4:
                ImageButton tempButton3 = findViewById(R.id.partPolygon3);
                ImageView tempButton3Overlay = findViewById(R.id.notPartPolygon3);
                tempButton3Overlay.setVisibility(View.INVISIBLE);
                tempButton3.setOnClickListener(this);
                tempButton3.setOnLongClickListener(this);
                partButtons.add(0,tempButton3);
                partStates.add(false);
            case 3:
                ImageButton tempButton2 = findViewById(R.id.partPolygon2);
                ImageView tempButton2Overlay = findViewById(R.id.notPartPolygon2);
                tempButton2Overlay.setVisibility(View.INVISIBLE);
                tempButton2.setOnClickListener(this);
                tempButton2.setOnLongClickListener(this);
                partButtons.add(0,tempButton2);
                partStates.add(false);
            case 2:
                ImageButton tempButton1 = findViewById(R.id.partPolygon1);
                ImageView tempButton1Overlay = findViewById(R.id.notPartPolygon1);
                tempButton1Overlay.setVisibility(View.INVISIBLE);
                tempButton1.setOnClickListener(this);
                tempButton1.setOnLongClickListener(this);
                partButtons.add(0,tempButton1);
                partStates.add(false);
            case 1:
                ImageButton tempButton0 = findViewById(R.id.partPolygon0);
                ImageView tempButton0Overlay = findViewById(R.id.notPartPolygon0);
                tempButton0Overlay.setVisibility(View.INVISIBLE);
                tempButton0.setOnClickListener(this);
                tempButton0.setOnLongClickListener(this);
                partButtons.add(0,tempButton0);
                partStates.add(false);
            default:
                break;
        }
    }


    private void assignAddresses() {
        ArrayList<TextView> addresses = new ArrayList<>();
        addresses.add(findViewById(R.id.filterAddress0));
        addresses.add(findViewById(R.id.filterAddress1));
        addresses.add(findViewById(R.id.filterAddress2));
        addresses.add(findViewById(R.id.filterAddress3));

        switch (userLocations.size()){
            case 4:
                addresses.get(3).setText(userLocations.get(3).getAddress().split(",")[0]);
            case 3:
                addresses.get(2).setText(userLocations.get(2).getAddress().split(",")[0]);
            case 2:
                addresses.get(1).setText(userLocations.get(1).getAddress().split(",")[0]);
            case 1:
                addresses.get(0).setText(userLocations.get(0).getAddress().split(",")[0]);
        }
    }

    private void fillHashTable() {
        buttonAmenityDictionary.put(R.id.filterBaseballButton,new Pair<>(0,baseballButton));
        buttonAmenityDictionary.put(R.id.filterBasketballButton,new Pair<>(1,basketballButton));
        buttonAmenityDictionary.put(R.id.filterBocceButton,new Pair<>(2,bocceButton));
        buttonAmenityDictionary.put(R.id.filterCricketButton,new Pair<>(3,cricketButton));
        buttonAmenityDictionary.put(R.id.filterRinkButton,new Pair<>(4,rinkButton));
        buttonAmenityDictionary.put(R.id.filterPlaysiteButton,new Pair<>(5,playsiteButton));
        buttonAmenityDictionary.put(R.id.filterSoccerButton,new Pair<>(6,soccerButton));
        buttonAmenityDictionary.put(R.id.filterSoftballButton,new Pair<>(7,softballButton));
        buttonAmenityDictionary.put(R.id.filterSpraypadButton,new Pair<>(8,spraypadButton));
        buttonAmenityDictionary.put(R.id.filterTennisButton,new Pair<>(9,tennisButton));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        if (item.getItemId() == R.id.action_map) {
            for(int i = 0; i < amenityFilter.size(); i++)
                amenityFilter.set(i,0);
            goToMap();
        }
        return true;
    }

    private void goToMap() {
        Log.d(TAG, "To Map");
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"Clicked: "+v.getId());
        if(buttonAmenityDictionary.containsKey(v.getId())) {
            amenityFilter.set(buttonAmenityDictionary.get(v.getId()).first, amenityFilter.get(buttonAmenityDictionary.get(v.getId()).first) ^ 1);
            if (amenityFilter.get(buttonAmenityDictionary.get(v.getId()).first) == 0) {
                Log.d(TAG,"Button Off");
                buttonAmenityDictionary.get(v.getId()).second.setBackgroundColor(Color.TRANSPARENT);
            } else {
                Log.d(TAG,"Button On");
                buttonAmenityDictionary.get(v.getId()).second.setBackgroundColor(getResources().getColor(R.color.purple_700));
            }
        }
        else if(v.getId() == R.id.filterButton){
            boolean filterByAmenity = false;
            for(int num:amenityFilter){
                if(num > 0){
                    filterByAmenity = true;
                    break;
                }
            }
            if(filterByAmenity) {
                Log.d(TAG,"Filter By Amenity");
                filterParksByAmenity();
            }
            if(aroundStates.contains(true)) {
                Log.d(TAG,"Filter By Around");
                filterParksAround();
            }
            if(partStates.contains(true)) {
                Log.d(TAG,"Filter By Part of Polygon");
                filterParksPolygon();
            }
            goToMap();
        }
        else if(v.getId() == R.id.partPolygon0){
            partButtonClicked(0);
        }
        else if(v.getId() == R.id.partPolygon1){
            partButtonClicked(1);
        }
        else if(v.getId() == R.id.partPolygon2){
            partButtonClicked(2);
        }
        else if(v.getId() == R.id.partPolygon3){
            partButtonClicked(3);
        }
        else if(v.getId() == R.id.aroundButton0){
            aroundButtonClicked(0);
        }
        else if(v.getId() == R.id.aroundButton1){
            aroundButtonClicked(1);
        }
        else if(v.getId() == R.id.aroundButton2){
            aroundButtonClicked(2);
        }
        else if(v.getId() == R.id.aroundButton3){
            aroundButtonClicked(3);
        }
    }

    private void filterParksPolygon() {
        Log.d(TAG,"StartparksToDrawSizeFilterParksPoly:"+parksToDraw.size());
        if(userLocations.size() == 2){
            Double lon0 = userLocations.get(0).getmPoint().getLongitude();
            Double lat0 = userLocations.get(1).getmPoint().getLatitude();
            Double lon1 = userLocations.get(1).getmPoint().getLongitude();
            Double lat1 = userLocations.get(1).getmPoint().getLatitude();
            GeoPoint mid = new GeoPoint((lat0+lat1)/2,(lon0+lon1)/2);
            MyCircle circle = new MyCircle(mid,userLocations.get(0).getmPoint());
            ArrayList<Park> newParksToDraw = new ArrayList<>(parksToDraw);
            for(Park park: parksToDraw){
                if(circle.contains(park.getmLocation())){
                    if(!newParksToDraw.contains(park))
                        newParksToDraw.add(park);
                }
            }
            parksToDraw = newParksToDraw;
        }
        else if(userLocations.size() > 2){
            GeoPoint[] corners = new GeoPoint[userLocations.size()];
            for(int i = 0; i<userLocations.size(); i++){
                corners[i] = userLocations.get(i).getmPoint();
            }
            ArrayList<Park> newParksToDraw = new ArrayList<>();
            Log.d(TAG,parksToDraw.size()+"");

            for(Park park:parksToDraw) {
                if(isInside(corners, userLocations.size(), park.getmLocation())){
                    newParksToDraw.add(park);
                }
            }
            parksToDraw = newParksToDraw;
        }
        Log.d(TAG,"EndparksToDrawSizeFilterParksPoly:"+parksToDraw.size());
    }

    //Change icon, add buffer filter thing i is position in arraylist of button
    private void aroundButtonClicked(int i) {
        Log.d(TAG,"Around Button Clicked"+ i);
        ImageButton buttonClicked = aroundButtons.get(i);
        Boolean state = aroundStates.get(i);
        if (state) {
            Log.d(TAG, "Turning Off Around Location");
            buttonClicked.setImageResource(R.drawable.not_around_location);
        } else {
            Log.d(TAG, "Turning On Around Location");
            buttonClicked.setImageResource(R.drawable.around_location);
        }
        aroundStates.set(i, !state);
    }

    //Change icon, add polygon filter thing
    private void partButtonClicked(int i) {
        Log.d(TAG,"PartBio Button Clicked"+ i);
        ImageButton buttonClicked = partButtons.get(i);
        Boolean state = partStates.get(i);
        if (state) {
            Log.d(TAG, "Turning Off Part Polygons");
            buttonClicked.setImageResource(R.drawable.not_part_polygon);
        } else {
            Log.d(TAG, "Turning On Part Polygons");
            buttonClicked.setImageResource(R.drawable.yes_part_polygon);
        }
        partStates.set(i, !state);
    }

    private boolean withinDist(GeoPoint point0, GeoPoint point1, double kmDist){
        double lat0 = point0.getLatitude();
        double lon0 = point0.getLongitude();
        double lat1 = point1.getLatitude();
        double lon1 = point1.getLongitude();

        if ((lat0 == lat1) && (lon0 == lon1)) {
            return true;
        }
        else {
            //From https://www.geodatasource.com/developers/java Euclidean Distance then convert to km
            double theta = lon0 - lon1;
            double dist = Math.sin(Math.toRadians(lat0)) * Math.sin(Math.toRadians(lat1)) + Math.cos(Math.toRadians(lat0)) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;

            //Convert to km
            dist = dist * 1.609344;
            return dist <= kmDist;
        }
    }

    private void filterParksAround(){
        Log.d(TAG,"StartparksToDrawSizeFilterParksAround:"+parksToDraw.size());
        ArrayList<Park> newParksToDraw = new ArrayList<>();
        for(Location location:userLocations){
            Log.d(TAG,"Distance for this location:"+location.getmSelectedDist());
            for(Park park: parksToDraw) {
                if (withinDist(location.getmPoint(), park.getmLocation(), location.getmSelectedDist()))
                    newParksToDraw.add(park);
            }
            parksToDraw = newParksToDraw;
        }
        Log.d(TAG,"EndparksToDrawSizeFilterParksAround:"+parksToDraw.size());
    }

    private void filterParksByAmenity() {
        Log.d(TAG,"StartparksToDrawSizefilterParksByAmenity:"+parksToDraw.size());
        int amountOfFilters = 0;
        for(int i = 0; i<10; i++){
            if(amenityFilter.get(i) != 0){
                amountOfFilters++;
            }
        }
        ArrayList<Park> newParksToDraw = new ArrayList<>();
        for(Park park:parksToDraw){
            int amountOfAmenities = 0;
            for(int j = 0; j < 10; j++)
                if (amenityFilter.get(j) > 0 && amountOfAmenities < amountOfFilters)
                    if (park.getmAmenityAmounts().get(j) >= amenityFilter.get(j)) {
                        amountOfAmenities++;
                        if(amountOfAmenities == amountOfFilters)
                            newParksToDraw.add(park);
                    }
        }
        parksToDraw = newParksToDraw;
        Log.d(TAG,"EndparksToDrawSizefilterParksByAmenity:"+parksToDraw.size());
    }

    @Override
    public void onBackPressed() {
        ((ManagingClass)getApplicationContext()).parksToDraw = parksToDraw;
        super.onBackPressed();
    }

    @Override
    public boolean onLongClick(View v) {
        Log.d(TAG,v.getId()+":"+R.id.aroundButton0);

        if(buttonAmenityDictionary.containsKey(v.getId())) {
            amenityFilter.set(buttonAmenityDictionary.get(v.getId()).first, 1);
            buttonAmenityDictionary.get(v.getId()).second.setBackgroundColor(getResources().getColor(R.color.purple_700));
            createAmenityPopup(buttonAmenityDictionary.get(v.getId()));
        }
        else if(v.getId() == R.id.aroundButton0){
            enterKmPopup(0);
        }
        else if(v.getId() == R.id.aroundButton1){
            enterKmPopup(1);
        }
        else if(v.getId() == R.id.aroundButton2){
            enterKmPopup(2);
        }
        else if(v.getId() == R.id.aroundButton3){
            enterKmPopup(3);
        }
        return false;
    }

    private void enterKmPopup(int pos) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View enterPopup = getLayoutInflater().inflate(R.layout.enter_km_popup,null);
        dialogBuilder.setView(enterPopup);
        dialog = dialogBuilder.create();
        dialog.show();
        EditText input = enterPopup.findViewById(R.id.enter_km_popup_input);
        Button confirm = enterPopup.findViewById(R.id.enter_km_popup_confirm);
        if(confirm != null) {
            confirm.setOnClickListener(v -> {
                if (input != null) {
                    String text = input.getText().toString();
                    Log.d(TAG,"Entered text "+text);
                    if (!text.isEmpty())
                        try {
                            userLocations.get(pos).setmSelectedDist(Double.parseDouble(text));
                            aroundButtons.get(pos).setImageResource(R.drawable.around_location);
                            aroundStates.set(pos,true);
                            // it means it is double
                        } catch (Exception e1) {
                            // this means it is not double
                            userLocations.get(pos).setmSelectedDist(0.75);
                            aroundButtons.get(pos).setImageResource(R.drawable.around_location);
                            aroundStates.set(pos,true);
                            e1.printStackTrace();
                        }
                    dialog.dismiss();
                }
            });
        }
    }

    public void createAmenityPopup(Pair<Integer,ImageButton> pair){
        int amenity = pair.first;
        dialogBuilder = new AlertDialog.Builder(this);
        final View amenityPopupView = getLayoutInflater().inflate(R.layout.amenity_popup,null);
        TextView amenityName = amenityPopupView.findViewById(R.id.PopupAmenityName);
        SeekBar amenitySeekBar = amenityPopupView.findViewById(R.id.PopUpSeekBar);
        TextView amenityHigh = amenityPopupView.findViewById(R.id.PopupAmenityHigh);

        dialogBuilder.setView(amenityPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        int highestAmount = getAmenityHigh(amenity);
        amenityName.setText(parksToDraw.get(0).getAmenityName(amenity));
        amenityHigh.setText(getString(R.string.highestAmenity,highestAmount));
        amenitySeekBar.setMax(highestAmount);
        amenitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                amenityFilter.set(amenity,seekBar.getProgress());
                if(seekBar.getProgress() > 0){
                    pair.second.setBackgroundColor(getResources().getColor(R.color.purple_700));
                }
                else{

                    pair.second.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }
        });
    }

    //Return the highest value of an amenity for purpose of building the scale bar
    private int getAmenityHigh(int amenity) {
        int toRet = 0;
        for(Park park: allParks){
            if(park.getmAmenityAmounts().get(amenity) > toRet){
                toRet = park.getmAmenityAmounts().get(amenity);
            }
        }
        return toRet;
    }

    public static boolean onSegment(GeoPoint p, GeoPoint q, GeoPoint r)
    {
        return q.getLatitude() <= Math.max(p.getLatitude(), r.getLatitude()) && q.getLatitude() >= Math.min(p.getLatitude(), r.getLatitude())
                && q.getLongitude() <= Math.max(p.getLongitude(), r.getLongitude()) && q.getLongitude() >= Math.min(p.getLongitude(), r.getLongitude());
    }

    public static double orientation(GeoPoint p, GeoPoint q, GeoPoint r)
    {
        double val = (q.getLongitude() - p.getLongitude()) * (r.getLatitude() - q.getLatitude()) - (q.getLatitude() - p.getLatitude()) * (r.getLongitude() - q.getLongitude());

        if (val == 0)
            return 0;
        return (val > 0) ? 1 : 2;
    }

    //Check if a line made from p1 to p2 intersects with the line made from q1 to q2
    public static boolean doIntersect(GeoPoint p1, GeoPoint q1, GeoPoint p2, GeoPoint q2)
    {

        double o1 = orientation(p1, q1, p2);
        double o2 = orientation(p1, q1, q2);
        double o3 = orientation(p2, q2, p1);
        double o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4)
            return true;
        if (o1 == 0 && onSegment(p1, p2, q1))
            return true;
        if (o2 == 0 && onSegment(p1, q2, q1))
            return true;
        if (o3 == 0 && onSegment(p2, p1, q2))
            return true;
        if (o4 == 0 && onSegment(p2, q1, q2))
            return true;

        return false;
    }

    public static boolean isInside(GeoPoint[] polygon, int n, GeoPoint p)
    {
        int INF = 10000;
        if (n < 3)
            return false;

        GeoPoint extreme = new GeoPoint(INF, p.getLongitude());

        int count = 0, i = 0;
        do{
            int next = (i + 1) % n;
            if (doIntersect(polygon[i], polygon[next], p, extreme))
            {
                if (orientation(polygon[i], p, polygon[next]) == 0)
                    return onSegment(polygon[i], p, polygon[next]);

                count++;
            }
            i = next;
        } while (i != 0);

        return (count & 1) == 1;
    }

}