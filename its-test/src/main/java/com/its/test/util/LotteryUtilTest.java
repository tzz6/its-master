package com.its.test.util;

import java.util.List;

import org.junit.Test;

import com.its.common.utils.LotteryUtil;
import com.its.common.utils.RewardEntry;

/**
 * 
 * @author tzz
 */
public class LotteryUtilTest {


	/** 判断是否全是同一名称 */
	public boolean isWinner(List<RewardEntry> rewardEntries) {
		boolean isWinner = true;
		for (RewardEntry rewardEntry : rewardEntries) {
			if (rewardEntries.get(0).getName() != rewardEntry.getName()) {
			    // 名称不全相同
				return false;
			}
		}
		return isWinner;
	}

	/** 测试 */
	@Test
	public void testLottery() {
		LotteryUtil lotteryUtil = new LotteryUtil();
		List<RewardEntry> rewardModelList = null;
		int end = 100;
		for (int i = 0; i < end; i++) {
			rewardModelList = lotteryUtil.getKeys(1);
			if (isWinner(rewardModelList)) {
				System.out.println("===第" + i + "次抽中===");
				for (RewardEntry rewardModel : rewardModelList) {
					System.out.println("name="+rewardModel.getName() + "," + "percent=" + rewardModel.getPercent());
				}
			}
		}
	}
	
	/** 测试 */
	@Test
	public void testGetLottery() {
		LotteryUtil lotteryUtil = new LotteryUtil();
		List<RewardEntry> rewardModelList = lotteryUtil.getKeys(1);
		System.out.println(rewardModelList.size());
	}
}