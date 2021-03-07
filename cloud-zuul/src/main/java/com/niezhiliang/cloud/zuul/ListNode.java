package com.niezhiliang.cloud.zuul;

/**
 * @Author : niezhiliang
 * @Date : 2021/3/3
 */
public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }


    /**
     * 1.处理边界条件，如果其中一个为空，无需进行任何操作，返回不为空的一个
     * 2.找到新链表的头结点，（比较两个量表的第一个值，小的为头结点
     * 3.找到两个链表当前的头结点（上面那个比较小的节点，要从第二个节点开始）
     * 4.记录新链表的当前节点（用于记录新链表走到的位置）
     * 5.当两个节点都不为空的时候，一直判断哪个节点需要放到新的链表中
     * 6.某一个链表为空后，直接把当前节点的下一个节点记录为剩余的节点
     * 7.最后返回head节点
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
          //处理边界
          if (l1 == null || l2 == null) {
              return l1 == null ? l2 : l1;
          }
          ListNode head = l1.val <= l2.val ? l1 : l2;
          ListNode cur1 = head.next;
          ListNode cur2 = head == l1 ? l2 : l1;
          ListNode pre = head;
          while (cur1 != null && cur2 != null) {
              if (cur1.val <= cur2.val) {
                  pre.next = cur1;
                  cur1 = cur1.next;
              } else {
                  pre.next = cur2;
                  cur2 = cur2.next;
              }
              pre = pre.next;
          }
        pre.next = cur1 == null ? cur2 : cur1;
         return head;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode();
        ListNode node2 = new ListNode();
        ListNode node3 = new ListNode();
        node1.next = node2;
        node2.next = node3;

        ListNode header = node1;
        int i = 1;
        while (header.next != null) {
            i++;
            header = header.next;
        }
        System.out.println(i);

    }
}
