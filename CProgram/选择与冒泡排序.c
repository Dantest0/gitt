#include <stdio.h>
#include <stdbool.h>

#define SIZE 10

//ѡ�������������ʱ�临�Ӷȶ�Ϊ��n^2���������Ż�
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

//ð������
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
        //������һ�ν���λ�ã����Ǵ�λ��֮���Ԫ�ض��Ѿ�������
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
        //������ȣ��������ѭ���в�û�з����κν�������Ԫ��ȫ�����򣬽�������
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
