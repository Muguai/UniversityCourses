using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameControllerTentacle : MonoBehaviour {

	

    public GameObject Hazards;
    public Vector3 SpawnValues;
    public int hazardCount;
    public Transform SpawnLocation1;
    public Transform SpawnLocation2;
    public Transform SpawnLocation3;
    public Transform SpawnLocation4;
    public Transform SpawnLocation5;
    public Transform SpawnLocation6;
    public Transform SpawnLocation7;
    public Transform SpawnLocation8;
    private Transform Reallocation;
    public float spawnWaitMin;
    public float spawnWaitMax;
    public float startWait;
    public float waveWait;
    private float spawnWaitbefore;
    public float MaxX = 7;
    public float MinX = -16;
    public float minRotation = 10;
    public float maxRotation = -90;
    public int maxArray = 7;
    Transform dumb;
   

    public float MinSpawnYvalue;
    public float MaxSpawnYvalue;
    Transform[] Array;
    private void Start()
    {

        Array = new Transform[8];
        SpawnAdd();

        StartCoroutine(SpawnWaves());
    }
    IEnumerator SpawnWaves()
    {
        yield return new WaitForSeconds(startWait);
        while (true)
        {
            for (int i = 0; i < hazardCount; i++)
            {
                
                int randomie = Random.Range(0, maxArray);
                Reallocation = Array[randomie];
                maxArray--;
                Array[randomie] = dumb;
                while(Reallocation == dumb)
                    {
                    Reallocation = Array[Random.Range(0,7)];
                    }
                //BouncyEnemy enemyScript = Hazards.GetComponent<BouncyEnemy>();
                //enemyScript.moveSpeed = Random.Range(moveSpeedMin, moveSpeedMax);
                //enemyScript.delta = Random.Range(bouncinessmin, bouncinessmax);
                //nemyScript.speed = Random.Range(bobSpeedmin, bobSpeedMax);
                float spawnWait = Random.Range(spawnWaitMin, spawnWaitMax);
                while (spawnWait == spawnWaitbefore)
                {
                    for (int a = 0; a < hazardCount; a++)
                        spawnWait = Random.Range(spawnWaitMin, spawnWaitMax);
                }


                Vector3 spawnPosition = new Vector3(Reallocation.transform.position.x, Reallocation.transform.position.y, Reallocation.transform.position.z);
                Quaternion spawnRotation = Quaternion.identity;
                Instantiate(Hazards, spawnPosition, spawnRotation);
                yield return new WaitForSeconds(spawnWait);
                spawnWaitbefore = spawnWait;
            }
            yield return new WaitForSeconds(16f);
            SpawnAdd();
        }
    }
    public void SpawnAdd()
    {
        Array[0] = SpawnLocation1;
        Array[1] = SpawnLocation2;
        Array[2] = SpawnLocation3;
        Array[3] = SpawnLocation4;
        Array[4] = SpawnLocation5;
        Array[5] = SpawnLocation6;
        Array[6] = SpawnLocation7;
        Array[7] = SpawnLocation8;
       
        maxArray = 7;
    }
}
