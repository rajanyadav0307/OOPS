package com.google.mediapipe.examples.handlandmarker;

import com.google.mediapipe.examples.handlandmarker.fragment.CameraFragment;
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult;
import java.util.List;

public class HandGestureController {
    static List<HandLandmarkerResult> handLandmarks;

    private static double getXcordinate(double point_x)
    {
        return point_x * ((Integer) OverlayView.m_width) * (((Float)OverlayView.m_scalefactor));
    }
    private static double getYcordinate(double point_y)
    {
        return point_y * ((Integer) OverlayView.m_height) * (((Float)OverlayView.m_scalefactor));
    }

    private static Integer HandsCount()
    {
        return handLandmarks.get(0).landmarks().size();
    }

    private static void Handness( int index)
    {
       System.out.println( handLandmarks.get(0).handednesses().get(0));
    }

    private static boolean OkGesture()
    {
        if(getYcordinate(handLandmarks.get(0).landmarks().get(0).get(4).y()) < getYcordinate(handLandmarks.get(0).landmarks().get(0).get(8).y())
        && getYcordinate(handLandmarks.get(0).landmarks().get(0).get(4).y()) < getYcordinate(handLandmarks.get(0).landmarks().get(0).get(0).y()))
        {
            return true;
        }
        return false;
    }

    private static void ResetOk()
    {
        CameraFragment.ok_count = 0;
    }

    private static void NavigateOk()
    {
        //System.out.println("Checking Okay");
        ResetUpDown();
        ResetLeftRight();
        CameraFragment.ok_count = ((Integer) CameraFragment.ok_count) + 1;
        if(CameraFragment.ok_count.equals(20)){
            ResetOk();
            System.out.println("Clicked");
        }
    }
    private static boolean LeftRightGesture()
    {
        if(getYcordinate(handLandmarks.get(0).landmarks().get(0).get(8).y()) < getYcordinate(handLandmarks.get(0).landmarks().get(0).get(5).y()) &&
                getYcordinate(handLandmarks.get(0).landmarks().get(0).get(12).y()) < getYcordinate(handLandmarks.get(0).landmarks().get(0).get(9).y()) &&
                getYcordinate(handLandmarks.get(0).landmarks().get(0).get(16).y()) < getYcordinate(handLandmarks.get(0).landmarks().get(0).get(13).y()) &&
                getYcordinate(handLandmarks.get(0).landmarks().get(0).get(20).y()) < getYcordinate(handLandmarks.get(0).landmarks().get(0).get(17).y())) {
            //System.out.println("Hand is Open");
            return true;
        }
        else
        {
            //System.out.println("Hand is Closed");
            return false;
        }
    }
    private static Double FindDistance(double px1, double py1, double px2, double py2) {
        double x_delta = px2 - px1;
        double y_delta = 0;

        return (Math.sqrt((x_delta * x_delta) + (y_delta * y_delta)));
    }

    private static boolean UpDownGesture()
    {
        if(getYcordinate(handLandmarks.get(0).landmarks().get(0).get(16).y()) > getYcordinate(handLandmarks.get(0).landmarks().get(0).get(13).y())
                &&
                getYcordinate(handLandmarks.get(0).landmarks().get(0).get(20).y()) > getYcordinate(handLandmarks.get(0).landmarks().get(0).get(17).y())
                &&
                /*getXcordinate(handLandmarks.get(0).landmarks().get(0).get(4).x()) < getXcordinate(handLandmarks.get(0).landmarks().get(0).get(5).x())
                &&*/
                getYcordinate(handLandmarks.get(0).landmarks().get(0).get(8).y()) < getYcordinate(handLandmarks.get(0).landmarks().get(0).get(5).y()))
        {
            return true;
        }
        return false;
    }
    private static void ResetUpDown()
    {
        CameraFragment.y_navup = 0;
        CameraFragment.y_navdown = 0;
        CameraFragment.y_count = 0;
        CameraFragment.y_nav = 0.0;

    }


    private static void NavigateUpDown()
    {
        //System.out.println("Checking Up and Down Gestures");
        ResetLeftRight();
        ResetOk();
        double point_y = getYcordinate(handLandmarks.get(0).landmarks().get(0).get(5).y());
        //System.out.println(point_y);
        double y_lo = ((double) CameraFragment.y_nav);
        double per_change = ((Math.abs(y_lo-point_y))/y_lo)*100;
        if(y_lo != 0.0 && point_y < y_lo && per_change > 5.0)
        {
            CameraFragment.y_navdown = 0;
            CameraFragment.y_navup = ((Integer) CameraFragment.y_navup) +1;
        }
        else if(y_lo != 0.0 && point_y > y_lo && per_change > 5.0)
        {
            CameraFragment.y_navup = 0;
            CameraFragment.y_navdown = ((Integer) CameraFragment.y_navdown) +1;
        }
        CameraFragment.y_count = ((Integer) CameraFragment.y_count) + 1;
        CameraFragment.y_nav = point_y;
        //System.out.println(CameraFragment.y_count);
        if(CameraFragment.y_count.equals(10))
        {
            Integer up = ((Integer) CameraFragment.y_navup);
            Integer down = ((Integer) CameraFragment.y_navdown);

            if( up > down)
            {
                System.out.println("Moved up");
            }
            else if( up < down)
            {
                System.out.println("Moved Down");
            }
            ResetUpDown();
        }

    }
    private static void ResetLeftRight()
    {
        CameraFragment.x_navleft =0;
        CameraFragment.x_navright=0;
        CameraFragment.x_count = 0;
        CameraFragment.x_nav = 0.0;
    }

    private static void NavigateLeftRight()
    {
            ResetUpDown();
            ResetOk();
            double point_x = handLandmarks.get(0).landmarks().get(0).get(20).x() * ((Integer) OverlayView.m_width) * (((Float)OverlayView.m_scalefactor));
            //System.out.println(point_x);
            //double x_distance = FindDistance(0.0, 0.0, point_x, 0.0);
            double x_lo= ((double) CameraFragment.x_nav);
            //System.out.println(x_distance);
            double per_change = ((Math.abs(x_lo-point_x))/x_lo)*100;
            if( x_lo !=0.0 && point_x > x_lo && per_change > 25.0 )
            {
                CameraFragment.x_navright = 0;
                CameraFragment.x_navleft =   ((Integer) CameraFragment.x_navleft) +1;
            }
            else if ( x_lo !=0.0 && point_x < x_lo && per_change > 25.0)
            {
                CameraFragment.x_navleft = 0;
                CameraFragment.x_navright =   ((Integer) CameraFragment.x_navright) +1;
            }
            CameraFragment.x_count = ((Integer) CameraFragment.x_count)+1;
            CameraFragment.x_nav = point_x;
            if(CameraFragment.x_count.equals(20))

            {
                Integer left = ((Integer) CameraFragment.x_navleft);
                Integer right = ((Integer) CameraFragment.x_navright);
                if(left > right)
                {
                    System.out.println("Moved Left");
                }
                else if(right > left)
                {
                    System.out.println("Moved Right");
                }

                ResetLeftRight();
            }
    }
    private static void StartModels()
    {
        if(LeftRightGesture()==true) {
            NavigateLeftRight();
        }
        else if(UpDownGesture()==true)
        {
            NavigateUpDown();
        }
        else if(OkGesture()==true)
        {
            NavigateOk();
        }

    }

    public static void ProcessGestureRecognition(List<HandLandmarkerResult> handLandmarkss)
    {
        handLandmarks = handLandmarkss;
        if(HandsCount()>0) {
            //System.out.println(HandsCount(handLandmarks)+"Hands Detected!!!");
            StartModels();
        }
        else
        {
            System.out.println("No Hands Detected!!!");
        }
    }

}
