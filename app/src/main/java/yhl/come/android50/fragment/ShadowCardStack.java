package yhl.come.android50.fragment;

/**
 * Created by yuhailong on 15/11/26.
 */
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import yhl.come.android50.R;

public class ShadowCardStack extends Fragment {

    private static final float X_SHIFT_DP = 1000;
    private static final float Y_SHIFT_DP = 50;
    private static final float Z_LIFT_DP = 8;
    private static final float ROTATE_DEGREES = 15;

    public AnimatorSet createSet(ArrayList<Animator> items, long startDelay) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(items);
        set.setStartDelay(startDelay);
        return set;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.shadow_card_stack,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        float density = getResources().getDisplayMetrics().density;

        final ViewGroup cardParent = (ViewGroup) view.findViewById(R.id.card_parent);

        final float X = X_SHIFT_DP * density;
        final float Y = Y_SHIFT_DP * density;
        final float Z = Z_LIFT_DP * density;

        ArrayList<Animator> towardAnimators = new ArrayList<>();
        ArrayList<Animator> expandAnimators = new ArrayList<>();
        ArrayList<Animator> moveAwayAnimators = new ArrayList<>();
        ArrayList<Animator> moveBackAnimators = new ArrayList<>();
        ArrayList<Animator> awayAnimators = new ArrayList<>();
        ArrayList<Animator> collapseAnimators = new ArrayList<>();

        final int max = cardParent.getChildCount();
        for (int i = 0; i < max; i++) {
            TextView card = (TextView) cardParent.getChildAt(i);
            card.setText("Card number " + i);

            float targetY = (i - (max-1) / 2.0f) * Y;
            Animator expand = ObjectAnimator.ofFloat(card, "translationY", targetY);
            expandAnimators.add(expand);

            Animator toward = ObjectAnimator.ofFloat(card, "translationZ", i * Z);
            toward.setStartDelay(200 * ((max) - i));
            towardAnimators.add(toward);

            card.setPivotX(X_SHIFT_DP);
            Animator rotateAway = ObjectAnimator.ofFloat(card, "rotationY",
                    i == 0 ? 0 : ROTATE_DEGREES);
            rotateAway.setStartDelay(200 * ((max) - i));
            rotateAway.setDuration(100);
            moveAwayAnimators.add(rotateAway);
            Animator slideAway = ObjectAnimator.ofFloat(card, "translationX",
                    i == 0 ? 0 : X);
            slideAway.setStartDelay(200 * ((max) - i));
            slideAway.setDuration(100);
            moveAwayAnimators.add(slideAway);

            Animator rotateBack = ObjectAnimator.ofFloat(card, "rotationY", 0);
            rotateBack.setStartDelay(200 * i);
            moveBackAnimators.add(rotateBack);
            Animator slideBack = ObjectAnimator.ofFloat(card, "translationX", 0);
            slideBack.setStartDelay(200 * i);
            moveBackAnimators.add(slideBack);

            Animator away = ObjectAnimator.ofFloat(card, "translationZ", 0);
            away.setStartDelay(200 * i);
            awayAnimators.add(away);

            Animator collapse = ObjectAnimator.ofFloat(card, "translationY", 0);
            collapseAnimators.add(collapse);
        }

        AnimatorSet totalSet = new AnimatorSet();
        totalSet.playSequentially(
                createSet(expandAnimators, 250),
                createSet(towardAnimators, 0),

                createSet(moveAwayAnimators, 250),
                createSet(moveBackAnimators, 0),

                createSet(awayAnimators, 250),
                createSet(collapseAnimators, 0));
        totalSet.start();
        totalSet.addListener(new RepeatListener(totalSet));
    }

    public static class RepeatListener implements Animator.AnimatorListener {
        final Animator mRepeatAnimator;
        public RepeatListener(Animator repeatAnimator) {
            mRepeatAnimator = repeatAnimator;
        }

        @Override
        public void onAnimationStart(Animator animation) {}

        @Override
        public void onAnimationEnd(Animator animation) {
            if (animation == mRepeatAnimator) {
                mRepeatAnimator.start();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    }
}