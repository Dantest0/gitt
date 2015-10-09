#include <stdio.h>
#include <stdbool.h>

#define SIZE 10

//选择排序，无论如何时间复杂度都为（n^2），难以优化
void selectSort(int *a,int size)
{
    int i,j;
    /*
    for(i=0;i<size-1;i++)
    {
        for(j=i+1;j<size;j++)
        {
            if(a[j]<a[i])
            {
                int t=a[j];
                a[j]=a[i];
                a[i]=t;
            }
        }
    }
    */
    int t;
    for(i=0;i<size-1;i++)
    {
        t=i;
        for(j=i+1;j<size;j++)
        {
            if(a[j]<a[t])
            {
                t=j;
            }
        }
        if(t!=i)
        {
            int m=a[t];
            a[t]=a[i];
            a[i]=m;
        }
    }
}

//冒泡排序，
void bubbleSort(int *a,int size)
{
    int i,j;
    /*
    bool change=true;
    for(i=0;i<size-1&&change;i++)
    {
        change=false;
        for(j=0;j<size-1-i;j++)
        {
            if(a[j]>a[j+1])
            {
                int t=a[j+1];
                a[j+1]=a[j];
                a[j]=t;
                change=true;
            }
        }
    }
    */
    int lastSwap;
    int lastSwapTemp=size-1;
    for(i=0;i<size-1;i++)
    {
        //标记最近一次交换位置，即那次位置之后的元素都已经有序了
        lastSwap=lastSwapTemp;
        for(j=0;j<lastSwap;j++)
        {

            if(a[j]>a[j+1])
            {
                int t=a[j];
                a[j]=a[j+1];
                a[j+1]=t;
                lastSwapTemp=j;
            }
        }
        //两者相等，即上面的循环中并没有发生任何交换，即元素全部有序，结束排序
        if(lastSwapTemp==lastSwap)
            break;
    }
}


int main(void)
{
    int a[SIZE]={1,5,9,8,3,4,2,6,7,0};
    int b[SIZE]={1,5,9,8,3,4,2,6,7,0};
    selectSort(a,SIZE);
    bubbleSort(b,SIZE);
    int i;
    for(i=0;i<SIZE;i++)
    {
        printf("%d ",*(a+i));
    }
    printf("\n");
    for(i=0;i<SIZE;i++)
    {
        printf("%d ",*(b+i));
    }
    printf("\n");
    return 0;
}
