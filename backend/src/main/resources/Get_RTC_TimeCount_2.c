#include<stdio.h>
#include<string.h>

int DeviceRTC[2];   //下发到串口设备的rtc时钟值。


unsigned long GetRtcTime(int year ,int month,int day ,int hour,int min ,int sec)
{
         unsigned long tm, temp, ytemp;
		 tm=0;
		 for(temp=1970; temp<year; temp++){		          //算出1970年到当看的秒数
			if(temp%4==0) tm=tm+((366*24)*3600);
			else tm=tm+((365*24)*3600);
		}
		ytemp=temp;
		for(temp=1; temp<month; temp++){		              //算出从1月到当前月的秒数
			if(ytemp%4==0&&temp==2) tm=tm+((29*24)*3600);
			else if(ytemp%4!=0&&temp==2) tm=tm+((28*24)*3600);
			else if(temp==4||temp==6||temp==9||temp==11) tm=tm+((30*24)*3600);
			else tm=tm+((31*24)*3600);
		}
		tm=tm+(((day-1)*24)*3600)+(hour*3600)+(min*60)+sec;
		return tm;
}

int main()
{
    unsigned long RTC_Count;
/*	int year, month,  day,  hour,  min,  sec;
    printf("输入年份:\n");     
    scanf("%d",&year);
    printf("输入月份:\n");     
    scanf("%d",&month);
    printf("输入日:\n");     
    scanf("%d",&day);
    printf("输入小时:\n");     
    scanf("%d",&hour);
    printf("输入分钟:\n");     
    scanf("%d",&min);
    printf("输入秒:\n");     
    scanf("%d",&sec);
    RTC_Count=GetRtcTime(year,month,day,hour,min,sec);   //计算出2018-8-14 21:13:09 时间点的RTC时钟值。*/
    RTC_Count=GetRtcTime(2018,8,14,21,13,9);   //计算出2018-8-14 21:13:09 时间点的RTC时钟值。5B73 45E5

		 DeviceRTC[0]=RTC_Count&0xffff;
		 DeviceRTC[1]=(RTC_Count>>16)&0xffff;

	printf("%x\n", RTC_Count);
	printf("DeviceRTC[0]:%04x\n", DeviceRTC[0]);
	printf("DeviceRTC[1]:%04x\n", DeviceRTC[1]);
	printf("DeviceRTC[0]:%d\n", DeviceRTC[0]);
	printf("DeviceRTC[1]:%d\n", DeviceRTC[1]);

    
	system("pause");
    
}