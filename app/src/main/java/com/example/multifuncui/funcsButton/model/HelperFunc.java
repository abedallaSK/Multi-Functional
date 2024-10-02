package com.example.multifuncui.funcsButton.model;



import static com.example.multifuncui.mangment.PrefManager.getPrimaryColor;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.example.multifuncui.R;
import com.example.multifuncui.funcsButton.model.enums.ActionType;
import com.example.multifuncui.funcsButton.model.enums.RoleFunc;
import com.example.multifuncui.funcsButton.model.enums.Type;
import com.example.multifuncui.funcsButton.page.FuncActivity;
import com.example.multifuncui.theme.ThemeActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HelperFunc {
    public static int defHomeColumnsSize() {return 3;}
    public static List< HomeItemModel > defFuncHome(Context context)
    {
        Set<RoleFunc> newRolesSet = new HashSet<>();
        newRolesSet.add(RoleFunc.ADMIN);
        newRolesSet.add(RoleFunc.USER);

        int primaryColor =getPrimaryColor(context);
        List< HomeItemModel > homeItemModel=new ArrayList<>();
        homeItemModel.add(new HomeItemModel(R.string.add_new_data,
                R.drawable.baseline_add_24,primaryColor,
             ActionType.ACTION_ADD_PROJECT,true, Type.TYPE_ACTIONS,R.string.create_new_project,
                newRolesSet));

        homeItemModel.add(new HomeItemModel(R.string.change_theme,
                R.drawable.baseline_color_lens_24,primaryColor,
                ActionType.ACTION_THEME,true, Type.TYPE_MANAGEMENT,R.string.textview,
                newRolesSet));
        homeItemModel.add(new HomeItemModel(R.string.kids_mode,
                R.drawable.baseline_child_care_24,primaryColor,
                ActionType.ACTION_ADD_PROJECT,true, Type.TYPE_MANAGEMENT,R.string.textview,
                newRolesSet));
        homeItemModel.add(new HomeItemModel(R.string.setting,
                R.drawable.baseline_settings_24,primaryColor,
                ActionType.ACTION_ADD_PROJECT,true, Type.TYPE_MANAGEMENT,R.string.textview,
                newRolesSet));
        homeItemModel.add(new HomeItemModel(R.string.checkroom,
                R.drawable.baseline_checkroom_24,primaryColor,
                ActionType.ACTION_ADD_PROJECT,true, Type.TYPE_MANAGEMENT,R.string.textview,
                newRolesSet));
        homeItemModel.add(new HomeItemModel(R.string.checkroom,
                R.drawable.baseline_checkroom_24,primaryColor,
                ActionType.ACTION_ADD_PROJECT,true, Type.TYPE_MANAGEMENT,R.string.textview,
                newRolesSet));
        homeItemModel.add(new HomeItemModel(R.string.checkroom,
                R.drawable.baseline_checkroom_24,primaryColor,
                ActionType.ACTION_ADD_PROJECT,true, Type.TYPE_MANAGEMENT,R.string.textview,
                newRolesSet));
        homeItemModel.add(new HomeItemModel(R.string.checkroom,
                R.drawable.baseline_checkroom_24,primaryColor,
                ActionType.ACTION_ADD_PROJECT,true, Type.TYPE_MANAGEMENT,R.string.textview,
                newRolesSet));
        homeItemModel.add(new HomeItemModel(R.string.checkroom,
                R.drawable.baseline_checkroom_24,primaryColor,
                ActionType.ACTION_ADD_PROJECT,true, Type.TYPE_MANAGEMENT,R.string.textview,
                newRolesSet));
        homeItemModel.add(new HomeItemModel(R.string.checkroom,
                R.drawable.baseline_checkroom_24,primaryColor,
                ActionType.ACTION_ADD_PROJECT,true, Type.TYPE_MANAGEMENT,R.string.textview,
                newRolesSet));
        homeItemModel.add(new HomeItemModel(R.string.checkroom,
                R.drawable.baseline_checkroom_24,primaryColor,
                ActionType.ACTION_ADD_PROJECT,false, Type.TYPE_MANAGEMENT,R.string.textview,
                newRolesSet));
        homeItemModel.add(new HomeItemModel(R.string.checkroom,
                R.drawable.baseline_checkroom_24,primaryColor,
                ActionType.ACTION_ADD_PROJECT,false, Type.TYPE_MANAGEMENT,R.string.textview,
                newRolesSet));


        return  homeItemModel;
    }


    public static void inFuncClick(ActionType position, Context context) {


//        try {
//            new GuessNumberTask(guessedNumber -> {
//            }). executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,user.getAuth0Id(), new ArrayList<>(user.getRoles()).get(0).toString(),String.valueOf(position));
//
//        }catch (Exception ignored){//TODO fix it
//        }


        switch (position){
            case ACTION_ADD_PROJECT:
               // goToActivity(CreateProjectActivity.class,context);
                break;
            case ACTION_THEME:
                goToActivity(ThemeActivity.class,context);
//                if (context instanceof Activity) {
//                    ((Activity) context).finish();
//                }
                break;
            case ACTION_ADD:
                goToActivity(FuncActivity.class,context);

                break;
            case REGISTRATIONS:
                //goToAdminActivity(AdminActivity.Register,context);
                break;
            default: Toast.makeText(context,String.valueOf(position),Toast.LENGTH_SHORT).show();
        }
    }



    private static void goToAdminActivity(int map,Context context) {
//        Intent intent=new Intent(context, AdminActivity.class);
//        intent.putExtra(AdminActivity.DATA, map);
//        context.startActivity(intent);
    }

    private static void gotoProjects(int mode, Context context) {
//        NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_activity_main);
//        Bundle args = new Bundle();
//        args.putInt(ProjectViewFragment.type_key, mode);
//        navController.navigate(R.id.action_navigation_home_to_projectViewFragment, args);
    }
    private static void goToActivity(Class<?> activityClass,Context context) {
        context.startActivity(new Intent(context, activityClass));
    }


}
