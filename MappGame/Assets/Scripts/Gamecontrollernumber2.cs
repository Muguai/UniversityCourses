using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Gamecontrollernumber2 : MonoBehaviour
{


    public GameObject Hazards2;
    public GameObject Hazards3;
    public GameObject Hazards4;
    // public Vector3 SpawnValues;
    public int hazardCount2;
    public Transform SpawnLocation;
    public Transform SpawnLocation22;
    // public Transform SpawnLocation3;
    // public Transform SpawnLocation4;
    // public Transform SpawnLocation5;
    // public Transform SpawnLocation6;
    // public Transform SpawnLocation7;
    // public Transform SpawnLocation8;
    // public Transform SpawnLocation9;
    // public Transform SpawnLocation10;
    // public Transform SpawnLocation11;
    // private Transform Reallocation;
    public float spawnWaitMin2;
    public float spawnWaitMax2;
    public float startWait2;
    public float waveWait2;
    public float angleXmin;
    public float angleXmax;
    public float angleYmin;
    public float angleYmax;
    public float Rotationmin;
    public float Rotationmax;

    private float spawnWaitbefore2;
    GameObject[] Array;
    // Transform[] Array;
    private void Start()
    {

        Array = new GameObject[3];
        Array[0] = Hazards2;
        Array[1] = Hazards3;
        Array[2] = Hazards4;
        //Array[3] = SpawnLocation4;
        //Array[4] = SpawnLocation5;
        // Array[5] = SpawnLocation6;
        // Array[6] = SpawnLocation7;
        // Array[7] = SpawnLocation8;
        //  Array[8] = SpawnLocation9;
        //  Array[9] = SpawnLocation10;
        //  Array[10] = SpawnLocation11;

        StartCoroutine(SpawnWaves2());
    }
    IEnumerator SpawnWaves2()
    {
        yield return new WaitForSeconds(startWait2);
        while (true)
        {
            for (int i = 0; i < hazardCount2; i++)
            {

                GameObject RealHazard = Array[Random.Range(0, 2)];
                Asteroid enemyScript = RealHazard.GetComponent<Asteroid>();
                enemyScript.angleX = Random.Range(angleXmin, angleXmax);
                enemyScript.angleY = Random.Range(angleYmin, angleYmax);
                enemyScript.rotationSpeed = Random.Range(Rotationmin, Rotationmax);

                float spawnWait = Random.Range(spawnWaitMin2, spawnWaitMax2);
                while (spawnWait == spawnWaitbefore2)
                {
                    for (int a = 0; a < hazardCount2; a++)
                        spawnWait = Random.Range(spawnWaitMin2, spawnWaitMax2);
                }
                // Reallocation = Array[Random.Range(0, 10)];
                Vector3 spawnPosition = new Vector3(Random.Range(SpawnLocation.transform.position.x, SpawnLocation22.transform.position.x), SpawnLocation.transform.position.y
                , 0f);
                Quaternion spawnRotation = Quaternion.identity;
                Instantiate(RealHazard, spawnPosition, spawnRotation);
                yield return new WaitForSeconds(spawnWait);
                spawnWaitbefore2 = spawnWait;
            }
            yield return new WaitForSeconds(waveWait2);
        }
    }
}
