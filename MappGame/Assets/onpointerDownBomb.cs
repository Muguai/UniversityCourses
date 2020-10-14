using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using UnityEngine.EventSystems;// Required when using Event data.

public class onpointerDownBomb : MonoBehaviour, IPointerUpHandler, IPointerDownHandler
{

    public GameObject Player;
    public float fireRate;
    public float nextFire;
    bool flag = false;
    private bool cooldown = false;
    public float shootTimer;
    public float shootCooldown;
    public void Update()
    {

      //  if (cooldown == false)
       // {
            //Do something
       //     Invoke("ResetCooldown", 5.0f);
        //    cooldown = true;
       // }

        if (flag)
        {
           if (Time.time > nextFire)
            {
                nextFire = Time.time + fireRate;
                Player.GetComponent<FiringBomb>().startFire();
            }


        }
    }
    //OnPointerDown is also required to receive OnPointerUp callbacks
    public void OnPointerDown(PointerEventData eventData)
    {
        flag = true;
    }

    //Do this when the mouse click on this selectable UI object is released.
    public void OnPointerUp(PointerEventData eventData)
    {
        flag = false;
    }
}
