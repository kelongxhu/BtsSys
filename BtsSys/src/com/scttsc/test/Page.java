package com.scttsc.test;

import java.util.List;

/**
 * Created by _think on 2014/9/17.
 */
public class Page<T> {
    private List<T> dataList;
    private Integer pageNow;
    private Integer total;
    private Integer start;
    private Integer end;
    private boolean hasNext=false;
    private boolean hasLast=false;


    public Page(Integer total, Integer pageNow) {
        this.total = total;
        this.pageNow = pageNow;
        initPageParam();
    }

    public void initPageParam(){
        //计算开始页码
         start=pageNow-4;
        //计算结束页
         end=pageNow+4;
        if(start<1){
            //如果开始页小于1，结束页加对应差值，开始页为1，
            end+=Math.abs(start)+1;
            start=1;
        }
        int pref=end-total;
        if(pref>0){
            //如果结束页大于总页数，結束頁為numPage
            end=total;
            start-=pref;
            if(start<0){
                start=1;
            }
        }
        //判断是否有上一页，下一页
        if(start>0){
            hasLast=true;
        }
        if(total>end){
           hasNext=true;
        }
    }


    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasLast() {
        return hasLast;
    }

    public void setHasLast(boolean hasLast) {
        this.hasLast = hasLast;
    }
}
