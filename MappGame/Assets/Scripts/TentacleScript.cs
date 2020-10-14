using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TentacleScript : MonoBehaviour {

    public float YangleMoveSpeed;
    private Vector3 v;

    public float minRotation = 10;
    public float maxRotation = -90;
    public float XangleMoveSpeed;
    public float dmg;

    void Start()
    {
        v = transform.position;
        var rotationVector = transform.rotation.eulerAngles;
        rotationVector.z = Random.Range(minRotation, maxRotation);
        transform.rotation = Quaternion.Euler(rotationVector);

    }

    void Update()
    {


        v.y += YangleMoveSpeed;
        v.x += XangleMoveSpeed;
        transform.position = v;
        //transform.position += Vector3.left * Time.deltaTime * 1.2f;




    }

    public void Boost(float Booster)
    {
        YangleMoveSpeed += Booster;
    }
    public void Stopper(float Booster)
    {
        YangleMoveSpeed *= Booster;
    }
    public void Lefting(float xBooster)
    {
        XangleMoveSpeed += xBooster;
    }


}
