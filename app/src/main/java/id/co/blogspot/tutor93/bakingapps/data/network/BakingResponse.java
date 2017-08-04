package id.co.blogspot.tutor93.bakingapps.data.network;

import java.util.List;

import id.co.blogspot.tutor93.bakingapps.data.model.Ingredient;
import id.co.blogspot.tutor93.bakingapps.data.model.Step;

/**
 * Created by indraaguslesmana on 8/4/17.
 */

public class BakingResponse {

    public Integer id;

    public String name;

    public List<Ingredient> ingredients = null;

    public List<Step> steps = null;

    public Integer servings;

    public String image;
}
