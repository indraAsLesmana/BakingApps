package id.co.blogspot.tutor93.bakingapps.data.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import id.co.blogspot.tutor93.bakingapps.data.model.Ingredient;
import id.co.blogspot.tutor93.bakingapps.data.model.Step;

/**
 * Created by indraaguslesmana on 8/4/17.
 */

public class BakingResponse implements Parcelable {

    public Integer id;

    public String name;

    public List<Ingredient> ingredients = null;

    public List<Step> steps = null;

    public Integer servings;

    public String image;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
        dest.writeValue(this.servings);
        dest.writeString(this.image);
    }

    public BakingResponse() {
    }

    protected BakingResponse(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.steps = in.createTypedArrayList(Step.CREATOR);
        this.servings = (Integer) in.readValue(Integer.class.getClassLoader());
        this.image = in.readString();
    }

    public static final Parcelable.Creator<BakingResponse> CREATOR = new Parcelable.Creator<BakingResponse>() {
        @Override
        public BakingResponse createFromParcel(Parcel source) {
            return new BakingResponse(source);
        }

        @Override
        public BakingResponse[] newArray(int size) {
            return new BakingResponse[size];
        }
    };
}
