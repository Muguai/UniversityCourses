using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Phase4Controller : MonoBehaviour {

    public GameObject Hazards;
    public Vector3 SpawnValues;
    public int hazardCount;
    public Transform SpawnLocation1;

    public float spawnWaitMin;
    public float spawnWaitMax;
    public float startWait;
    public float waveWait;
    private float spawnWaitbefore;
    public float MaxX = 7;
    public float MinX = -16;
    public float minRotation = 10;
    public float maxRotation = -90;



    private void Start()
    {



        StartCoroutine(SpawnWaves());
    }
    IEnumerator SpawnWaves()
    {
        yield return new WaitForSeconds(startWait);
        while (true)
        {
            for (int i = 0; i < hazardCount; i++)
            {

               
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


                Vector3 spawnPosition = new Vector3(Random.Range(MinX, MaxX), SpawnLocation1.transform.position.y, SpawnLocation1.transform.position.z);
                Quaternion spawnRotation = Quaternion.identity;
                Instantiate(Hazards, spawnPosition, spawnRotation);
                yield return new WaitForSeconds(spawnWait);
                spawnWaitbefore = spawnWait;
            }
            yield return new WaitForSeconds(16f);
           
        }
    }
   
}
