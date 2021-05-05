import java.io.*;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


interface Dummy {
    public default void RandomNumber(int[] a, int num) {        //배열을 랜덤한 숫자로 채움
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            a[i] = random.nextInt(99999);
        }
    }

    public default void ReverseNumber(int[] a, int num) {       //배열을 역순으로 정렬된 숫자로 채움

        for (int i = 0; i < num; i++) {
            a[i] = (num - i)*19;
        }

    }

    public default void PartiallySorted(int[] a, int num) {     //배열을 일부분만 정렬하고 나머지는 랜덤으로 채움
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            a[i] = random.nextInt(1000);
        }

        int r = random.nextInt(num - (num / 10) - 1);     //랜덤한 위치를 정해서 그 위치부터 배열의 10% 크기의 정렬된 수들을 넣어주었다.

        for (int i = 0; i < num / 10; i++) {
            a[r + i] = i + 1;
        }

    }

    public default void Sorted(int[] a, int num) {
        for (int i = 0; i < num; i++) {
            a[i] = i;
        }
    }
}

interface Sorting {
    public void sort(int[] a, int b);   //정렬하려는 배열과 배열의 길이를 입력으로 받음
}

public class Sort {
    static class DummyData implements Dummy {
    }

    static class BubbleSort implements Sorting {        //버블 정렬
        public void sort(int[] a, int num) {
            for (int i = 0; i < num - 1; i++) {
                for (int j = 0; j < num - i - 1; j++) {
                    if (a[j] > a[j + 1]) {
                        int temp = a[j + 1];
                        a[j + 1] = a[j];
                        a[j] = temp;
                    }
                }
            }
        }
    }

    static class SelectionSort implements Sorting {     //선택 정렬
        public void sort(int[] a, int num) {
            for (int i = 0; i < num - 1; i++) {
                int min = i;
                for (int j = i + 1; j < num; j++) {
                    if (a[j] < a[min]) min = j;
                }
                int temp = a[i];
                a[i] = a[min];
                a[min] = temp;
            }
        }
    }

    static class InsertionSort implements Sorting {     //삽입 정렬
        public void sort(int[] a, int num) {
            for (int i = 1; i < num; i++) {
                int currentelement = a[i];
                int j = i - 1;
                while (j >= 0 && a[j] > currentelement) {
                    a[j + 1] = a[j];
                    j = j - 1;
                }
                a[j + 1] = currentelement;
            }

        }
    }

    static class ShellSort implements Sorting {         //쉘 정렬
        public void sort(int[] a, int num) {

            int n = 0;
            int[] gapCiura = {1, 4, 10, 23, 57, 132, 301, 701, 1750, 3937, 8858, 19930, 44842, 100894, 227011,
                    510774, 1149241, 2585792, 5818032, 13090572, 29453787, 66271020, 149109795, 335497038,
                    754868335, 1698453753};     //ciura 갭은 1750까지만 나와 있지만 위키피디아를 참조해서 이 후 갭은 2.25씩 곱해서 정수형의 최대치인 21억 밑에 있는  1698453753까지만 고려했다.
            for (int i = 0; i < 26; i++) {
                if (num < gapCiura[i]) {        //배열의 크기보다 gap이 작도록 설정해주었다.
                    n = i - 1;
                    break;
                }
            }
            int[] gap = new int[n];
            for (int i = 0; i < n; i++) {
                gap[i] = gapCiura[i];           //배열의 크기보다 작은 gap들만 모아놓은 배열을 만들었다.
            }


            for (int x = 0; x < gap.length; x++) {
                int h = gap[gap.length - 1 - x];    //gap이 큰 순서대로 실행해주었다.
                for (int i = h; i < num; i++) {
                    int currentelement = a[i];
                    int j = i;
                    while (j >= h && a[j - h] > currentelement) {
                        a[j] = a[j - h];
                        j = j - h;
                    }
                    a[j] = currentelement;
                }
            }
        }
    }

    static class WriteExcel {           //정렬 알고리즘을 실행시켜 나온 3차원 실행시간 데이터를 엑셀 파일로 내보내는 코드

        public void writeExcel(int[][][] data) throws FileNotFoundException {
            try {
                File file = new File("C:\\Users\\p0306\\Desktop\\data.xlsx");
                FileOutputStream fileout = new FileOutputStream(file);

                XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
                XSSFSheet xssfSheet = xssfWorkbook.createSheet("data");
                XSSFRow curRow;

                XSSFCell cell = null;

                curRow = xssfSheet.createRow(0);
                cell = curRow.createCell(0);
                cell.setCellValue("데이터의 종류");

                curRow = xssfSheet.createRow(1);
                cell = curRow.createCell(0);
                cell.setCellValue("정렬 방법");

                curRow = xssfSheet.createRow(2);
                cell = curRow.createCell(1);
                cell.setCellValue("랜덤");

                cell = curRow.createCell(5);
                cell.setCellValue("역으로 정렬");

                cell = curRow.createCell(9);
                cell.setCellValue("부분적 정렬");

                curRow = xssfSheet.createRow(3);
                cell = curRow.createCell(0);
                cell.setCellValue("배열 길이");


                cell = curRow.createCell(1);
                cell.setCellValue("랜덤 버블");
                cell = curRow.createCell(2);
                cell.setCellValue("랜덤 선택");
                cell = curRow.createCell(3);
                cell.setCellValue("랜덤 삽입");
                cell = curRow.createCell(4);
                cell.setCellValue("랜덤 쉘");
                cell = curRow.createCell(5);
                cell.setCellValue("부분 버블");
                cell = curRow.createCell(6);
                cell.setCellValue("부분 선택");
                cell = curRow.createCell(7);
                cell.setCellValue("부분 삽입");
                cell = curRow.createCell(8);
                cell.setCellValue("부분 쉘");
                cell = curRow.createCell(9);
                cell.setCellValue("역 버블");
                cell = curRow.createCell(10);
                cell.setCellValue("역 선택");
                cell = curRow.createCell(11);
                cell.setCellValue("역 삽입");
                cell = curRow.createCell(12);
                cell.setCellValue("역 쉘");

                for (int i = 0; i < 30; i++) {
                    curRow = xssfSheet.createRow(4 + i);
                    cell = curRow.createCell(0);
                    cell.setCellValue((i + 1) * 2000);
                    for (int j = 0; j < 3; j++) {
                        cell = curRow.createCell(4 * j + 1);
                        cell.setCellValue(data[j][0][i]);
                        cell = curRow.createCell(4 * j + 2);
                        cell.setCellValue(data[j][1][i]);
                        cell = curRow.createCell(4 * j + 3);
                        cell.setCellValue(data[j][2][i]);
                        cell = curRow.createCell(4 * j + 4);
                        cell.setCellValue(data[j][3][i]);
                    }
                }

                xssfWorkbook.write(fileout);
                fileout.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

    public static void main(String[] args) {
        DummyData dummyData = new DummyData();
        BubbleSort bubbleSort = new BubbleSort();
        SelectionSort selectionSort = new SelectionSort();
        InsertionSort insertionSort = new InsertionSort();
        ShellSort shellSort = new ShellSort();
        WriteExcel writeExcel = new WriteExcel();


        int num = 1000000;
        int[] Array = new int[num];

        dummyData.ReverseNumber(Array, num);

        long start0 = System.currentTimeMillis();
        shellSort.sort(Array, num);
        long end0 = System.currentTimeMillis();
        long timediff0 = end0 - start0;
        System.out.printf("%d", timediff0);

    }

/*
    public static void main(String[] args) throws FileNotFoundException {

        DummyData dummyData = new DummyData();
        BubbleSort bubbleSort = new BubbleSort();
        SelectionSort selectionSort = new SelectionSort();
        InsertionSort insertionSort = new InsertionSort();
        ShellSort shellSort = new ShellSort();
        WriteExcel writeExcel = new WriteExcel();

        int[][][] data = new int[3][4][30];

        for (int x = 0; x < 3; x++) {               //숫자의 입력 종류별로 반복실행
            for (int y = 0; y < 4; y++) {           //정렬의 방법 종류별로 반복실행
                for (int z = 1; z < 30; z++) {      //입력의 개수별로 반복실행 (z*100개)

                    int num = z * 2000;             //배열의 길이
                    int[] Array = new int[num];

                    switch (x) {
                        case 0:
                            dummyData.RandomNumber(Array, num);         //랜덤한 숫자로 배열을 채움
                            break;
                        case 1:
                            dummyData.PartiallySorted(Array, num);      //일부 정렬된 숫자로 배열을 채움
                            break;
                        case 2:
                            dummyData.ReverseNumber(Array, num);        //역으로 정렬된 숫자로 배열을 채움
                            break;
                    }

                    switch (y) {
                        case 0:
                            long start0 = System.currentTimeMillis();
                            bubbleSort.sort(Array, num);                //버블 정렬로 배열을 정렬
                            long end0 = System.currentTimeMillis();
                            long timediff0 = end0 - start0;
                            data[x][y][z] = (int) timediff0;            //버블 정렬로 정렬하는데 걸린 시간을 data에 저장
                            break;
                        case 1:
                            long start1 = System.currentTimeMillis();
                            selectionSort.sort(Array, num);             //선택 정렬로 배열을 정렬
                            long end1 = System.currentTimeMillis();
                            long timediff1 = end1 - start1;
                            data[x][y][z] = (int) timediff1;            //선택 정렬로 정렬하는데 걸린 시간을 data에 저장
                            break;
                        case 2:
                            long start2 = System.currentTimeMillis();
                            insertionSort.sort(Array, num);             //삽입 정렬로 배열을 정렬
                            long end2 = System.currentTimeMillis();
                            long timediff2 = end2 - start2;
                            data[x][y][z] = (int) timediff2;            //삽입 정렬로 정렬하는데 걸린 시간을 data에 저장
                            break;
                        case 3:
                            long start3 = System.currentTimeMillis();
                            shellSort.sort(Array, num);                 //쉘 정렬로 배열을 정렬
                            long end3 = System.currentTimeMillis();
                            long timediff3 = end3 - start3;
                            data[x][y][z] = (int) timediff3;            //쉘 정렬로 정렬하는데 걸린 시간을 data에 저장
                            break;
                    }
                }
            }
        }
        writeExcel.writeExcel(data);        //실행 시간을 담은 3차원 배열을 엑셀 파일로 내보내기

    }
*/

}
