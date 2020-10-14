using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameControllerTurret : MonoBehaviour {

    public GameObject Hazards5;
    public int hazardCount3;
    public Transform SpawnLocation11;
    // public Transform SpawnLocation2;
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
    public float spawnWaitMin3;
    public float spawnWaitMax3;
    public float startWait3;
    public float waveWait3;
    
    //Transform[] Array;
    private void Start()
    {

        //   Array = new Transform[11];
        //   Array[0] = SpawnLocation1;
        //   Array[1] = SpawnLocation2;
        //  Array[2] = SpawnLocation3;
        // Array[3] = SpawnLocation4;
        //Array[4] = SpawnLocation5;
        // Array[5] = SpawnLocation6;
        // Array[6] = SpawnLocation7;
        // Array[7] = SpawnLocation8;
        //  Array[8] = SpawnLocation9;
        //  Array[9] = SpawnLocation10;
        //  Array[10] = SpawnLocation11;

        StartCoroutine(SpawnWaves());
    }
    IEnumerator SpawnWaves()
    {
        yield return new WaitForSeconds(startWait3);
        while (true)
        {
            for (int i = 0; i < hazardCount3; i++)
            {
                // Reallocation = Array[Random.Range(0, 10)];
                float spawnWait = Random.Range(spawnWaitMin3, spawnWaitMax3);
              
                Vector3 spawnPosition = new Vector3(SpawnLocation11.transform.position.x, SpawnLocation11.transform.position.y
                , 0f);
                Quaternion spawnRotation = Quaternion.identity;
                Instantiate(Hazards5, spawnPosition, spawnRotation);
                yield return new WaitForSeconds(spawnWait);
                
            }
            yield return new WaitForSeconds(waveWait3);
        }
    }
}
