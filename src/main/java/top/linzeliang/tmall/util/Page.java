package top.linzeliang.tmall.util;

public class Page {
    // 开始页数
    private int start;
    // 每页条数
    private int count;
    // 总条数
    private int total;
    // 参数
    private String param;

    // 默认每页5条
    private static final int defaultCount = 5;

    public Page() {
        this.count = defaultCount;
    }

    public Page(int start, int count) {
        this();
        this.start = start;
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public boolean isHasPreviouse() {
        if (0 == start) {
            return false;
        }
        return true;
    }

    public boolean isHasNext() {
        if (getLast() == start) {
            return false;
        }
        return true;
    }

    public int getTotalPage() {
        int totalPage;

        if (0 == total % count) {
            totalPage = total / count;
        } else {
            totalPage = total / count + 1;
        }

        if (0 == totalPage) {
            totalPage = 1;
        }
        return totalPage;
    }

    public int getLast() {
        int last = 0;

        if (0 == total % count) {
            last = total - count;
        } else {
            last = total - total % count;
        }
        last = last < 0 ? 0 : last;

        return last;
    }

    @Override
    public String toString() {
        return "Page [start=" + start + ", count=" + count + ", total=" + total + ", getStart()=" + getStart()
                + ", getCount()=" + getCount() + ", isHasPreviouse()=" + isHasPreviouse() + ", isHasNext()="
                + isHasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + "]";
    }
}
