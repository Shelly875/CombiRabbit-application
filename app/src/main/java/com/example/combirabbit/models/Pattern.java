package com.example.combirabbit.models;

import com.example.combirabbit.R;

import java.util.ArrayList;

public class Pattern {

    private ArrayList<ImagePlace> arrImgPattern;
    private int imgLevel;

    public Pattern(int newImage){

        // get the image of the level
        this.imgLevel = newImage;

        // init the pattern of each picture
        this.arrImgPattern = new ArrayList<>();

        // check what pattern give that image
        if(this.imgLevel == R.drawable.img_comb_one){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_red_rhombus_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_orange_square_img,
                    0));
        }

        if(this.imgLevel == R.drawable.img_comb_two){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_blue_half_circle_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_half_star_img,
                    0));
        }

        if(this.imgLevel == R.drawable.img_comb_three){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_red_rhombus_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_light_green_rec_squre_img,
                    90));
        }

        if(this.imgLevel == R.drawable.img_comb_four){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_red_quarter_circle_img,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_blue_half_circle_img,
                    180));
        }

        if(this.imgLevel == R.drawable.img_comb_five){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_orange_triangle_img,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_blue_trapeze_img,
                    270));
        }

        if(this.imgLevel == R.drawable.img_comb_six){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_star_backgroud_colored,
                    270));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_blue_trapeze_img,
                    90));
        }

        if(this.imgLevel == R.drawable.img_comb_seven){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_yellow_square_img,
                    180));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_green_rectangle_img,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_red_rhombus_img,
                    0));

            //TODO: maybe it is worth to add a flag to each ImagePlace(img, degree, flag=0/1)
            // the will indicate if it can be another possible match
        }

        if(this.imgLevel == R.drawable.img_comb_eight){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_half_star_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_circle_in_circle_block,
                    180));
        }

        if(this.imgLevel == R.drawable.img_comb_nine){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_red_rhombus_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_orange_square_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_light_green_rec_squre_img,
                    0));
        }

        if(this.imgLevel == R.drawable.img_comb_ten){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_blue_half_circle_img,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_yellow_lines_img,
                    270));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_star_backgroud_colored,
                    270));
        }

        if(this.imgLevel == R.drawable.img_comb_eleven){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_green_rectangle_img,
                    270));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_half_star_img,
                    180));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_green_parallelogram_img,
                    270));
        }

        if(this.imgLevel == R.drawable.img_comb_twelve){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_light_green_rec_squre_img,
                    180));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_red_quarter_circle_img,
                    180));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_blue_trapeze_img,
                    270));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_star_backgroud_colored,
                    0));
        }

        if(this.imgLevel == R.drawable.img_comb_thirteen){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_blue_half_circle_img,
                    180));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_purple_rec_circle_out_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_blue_trapeze_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_purple_half_circle_rec_img,
                    0));
        }

        if(this.imgLevel == R.drawable.img_comb_fourteen){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_red_rhombus_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_yellow_square_img,
                    270));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_orange_square_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_orange_triangle_img,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_blue_trapeze_img,
                    180));
        }

        if(this.imgLevel == R.drawable.img_comb_fiftheen){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_light_green_rec_squre_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_yellow_lines_img,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_yellow_square_img,
                    270));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_light_green_triangle_img,
                    180));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_circle_in_circle_block,
                    180));
        }

        if(this.imgLevel == R.drawable.img_comb_sixteen){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_green_rectangle_img,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_block_triangular_img,
                    180));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_green_parallelogram_img,
                    270));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_circle_in_circle_block,
                    180));
        }

        if(this.imgLevel == R.drawable.img_comb_seventeen){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_orange_triangle_img,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_half_star_img,
                    270));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_orange_square_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_light_green_rec_squre_img,
                    270));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_block_triangular_img,
                    270));
        }

        if(this.imgLevel == R.drawable.img_comb_eighteen){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_green_rectangle_img,
                    180));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_light_green_rec_squre_img,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_blue_trapeze_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_green_parallelogram_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_red_rhombus_img,
                    0));
        }

        if(this.imgLevel == R.drawable.img_comb_nineteen){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_orange_square_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_blue_half_circle_img,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_block_triangular_img,
                    0));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_orange_triangle_img,
                    90));
        }

        if(this.imgLevel == R.drawable.img_comb_twenty){
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_purple_rec_circle_out_img,
                    270));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_orange_triangle_img,
                    180));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_star_backgroud_colored,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_purple_half_circle_rec_img,
                    90));
            this.arrImgPattern.add(new ImagePlace(R.drawable.match_circle_in_circle_block,
                    0));
        }
    }

    public void set_imgPattern(ArrayList<ImagePlace> newImgPattern){
        this.arrImgPattern = newImgPattern;
    }

    public ArrayList<ImagePlace> get_ImgPattern(){
        return this.arrImgPattern;
    }

    public int getImgLevel() {
        return imgLevel;
    }

    public void setImgLevel(int imgLevel) {
        this.imgLevel = imgLevel;
    }
}


