/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * Delegate for AbstractApplicationContext's post-processor handling.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 4.0
 */
final class PostProcessorRegistrationDelegate {

	private PostProcessorRegistrationDelegate() {
	}


	public static void invokeBeanFactoryPostProcessors(
			ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {

		// Invoke BeanDefinitionRegistryPostProcessors first, if any.

		// 无论是什么情况 优先执行BeanDefinitionRegistryPostprocessors,将已经执行过的BFPP存储在processedBean中，防止重复执行
		Set<String> processedBeans = new HashSet<>();
		// 判断beanFactory是否是BeanDefinitionRegistry类型，此处是DefaultListableBeanFactory，实现了BeanDefinitionRegistry接口，所以为true
		if (beanFactory instanceof BeanDefinitionRegistry) {
			// 类型转换
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
			// BeanDefinitionRegistryPostProcessor是BeanFactoryPostProcessor的子集
			// BeanFactoryPostProcessor主要针对的操作对象是BeanFactory，而BeanDefinitionRegistryPostProcessor主要针对的操作对象是BeanDefinition
			// 存放BeanFactoryPostProcessor(父类)的集合
			List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
			// 存放BeanDefinitionRegistryPostProcessor(子类)的集合
			List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();
			// 首先处理入参中的beanFactoryPostprocessors,遍历所有的beanFactoryPostProcessors,将BeanDefinitionRegistryPostProcessor和BeanFactoryPostProcessor区分开
			for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
				// 如果是BeanDefinitionRegistryPostProcessor
				if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
					BeanDefinitionRegistryPostProcessor registryProcessor =
							(BeanDefinitionRegistryPostProcessor) postProcessor;
					// 直接执行BeanDefinitionRegistryPostProcessor接口中的postProcessBeanDefinitionRegistry方法
					registryProcessor.postProcessBeanDefinitionRegistry(registry);
					// 添加到registryProcessors 用于后续执行postProcessorBeanFactory方法
					registryProcessors.add(registryProcessor);
				} else {
					// 否则只是普通的BeanFactoryPostProcessor，添加到regularPostProcessors，用于后续执行postProcessorBeanFactory方法
					regularPostProcessors.add(postProcessor);
				}
			}

			// Do not initialize FactoryBeans here: We need to leave all regular beans
			// uninitialized to let the bean factory post-processors apply to them!
			// Separate between BeanDefinitionRegistryPostProcessors that implement
			// PriorityOrdered, Ordered, and the rest.
			// 用于保存本次要执行的BeanDefinitionRegistryPostProcessor
			List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();


			// First, invoke the BeanDefinitionRegistryPostProcessors that implement PriorityOrdered.
			// 调用所有实现PriorityOrdered接口的BeanDefinitionRegistryPostProcessor实现类
			// 找到所有实现BeanDefinitionRegistryPostProcessor接口的bean的beanName
			String[] postProcessorNames =
					beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			// 遍历处理所有符合规则的postProcessorNames
			for (String ppName : postProcessorNames) {
				// 检测是否实现了PriorityOrdered接口
				if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
					// 获取名字对应的bean实例,添加到currentRegistryProcessors中
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					// 将要被执行的BFPP名称添加到processedBeans,避免后续重复执行
					processedBeans.add(ppName);
				}
			}
			// 按照优先级进行排序操作
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			// 添加到registryProcessors中，用于最后执行postProcessBeanFactory方法
			registryProcessors.addAll(currentRegistryProcessors);
			// 遍历currentRegistryProcessors 执行postProcessBeanDefinitionRegistry方法
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			// 执行完毕之后，清空currentRegistryProcessors
			currentRegistryProcessors.clear();


			// 调用所有实现Ordered接口的BeanDefinitionRegistryPostProcessor实现类
			// 找到所有实现BeanDefinitionRegistryPostprocessor接口bean的beanName 此处需要重复查找的原因在于上面的执行过程可能会新增其他的BeanDefinitionRegistryPostprocessor
			// Next, invoke the BeanDefinitionRegistryPostProcessors that implement Ordered.
			postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			for (String ppName : postProcessorNames) {
				// 检测是否实现了Ordered接口，并且还未执行过
				if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
					// 获取名字对象的bean实例，添加到currentRegistryProcessors中
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					// 将要被执行的BFPP名称添加到processedBeans，避免后续重复执行
					processedBeans.add(ppName);
				}
			}
			// 按照优先级进行排序操作
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			// 添加到registryProcessors中，用于最后执行postProcessBeanFactory方法
			registryProcessors.addAll(currentRegistryProcessors);
			// 遍历currentRegistryProcessors 执行postProcessBeanDefinitionRegistry方法
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			// 执行完毕之后，清空currentRegistryProcessors
			currentRegistryProcessors.clear();


			// 最后，调用所有剩下的BeanDefinitionRegistryPostProcessors
			// Finally, invoke all other BeanDefinitionRegistryPostProcessors until no further ones appear.
			boolean reiterate = true;
			while (reiterate) {
				reiterate = false;
				// 找出所有实现BeanDefinitionRegistryPostProcessor接口的类
				postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
				// 遍历执行
				for (String ppName : postProcessorNames) {
					// 跳过已经执行过的BeanDefinitionRegistryPostProcessor
					if (!processedBeans.contains(ppName)) {
						// 获取名字对应的bean实例，添加到currentRegistryProcessor中
						currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
						// 将要被执行的BFPP名称添加到processedBeans，避免后续重复执行
						processedBeans.add(ppName);
						reiterate = true;
					}
				}
				// 按照优先级进行排序操作
				sortPostProcessors(currentRegistryProcessors, beanFactory);
				// 添加到registryProcessors中，用于最后执行postProcessBeanFactory方法
				registryProcessors.addAll(currentRegistryProcessors);
				// 遍历currentRegistryProcessors 执行postProcessBeanDefinitionRegistry方法
				invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
				// 执行完毕之后，清空currentRegistryProcessors
				currentRegistryProcessors.clear();
			}

			// Now, invoke the postProcessBeanFactory callback of all processors handled so far.
			// 调用所有BeanDefinitionRegistryPostProcessor的postProcessBeanFactory方法 (先调用子类的方法统一执行)
			invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
			// 最后，调用入参beanFactoryPostProcessors中普通BeanFactoryPostProcessor的postProcessBeanFactory方法 (再调用父类的方法统一执行)
			invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
		} else {
			// Invoke factory processors registered with the context instance.
			// 如果beanFactory不归属于BeanDefinitionRegistry类型，那么直接执行postProcessBeanFactory方法
			invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
		}




		// 到此，入参beanFactoryPostProcessors和容器中所有的BeanDefinitionRegistryPostProcessor已经全部处理完毕
		// 下面开始处理容器中所有的BeanFactoryPostProcessor
		// 可能会包含一些实现类，只实现了BeanFactoryPostProcessor，并没有实现BeanDefinitionRegistryPostProcessor接口

		// Do not initialize FactoryBeans here: We need to leave all regular beans
		// uninitialized to let the bean factory post-processors apply to them!
		// 找到所有实现BeanFactoryPostProcessor接口的类
		String[] postProcessorNames =
				beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);

		// Separate between BeanFactoryPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.
		// 用于存放实现了PriorityOrdered接口的BeanFactoryPostProcessor
		List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
		// 用于存放实现了Ordered接口的BeanFactoryPostProcessor的beanName
		List<String> orderedPostProcessorNames = new ArrayList<>();
		// 用于存放普通BeanFactoryPostProcessor的beanName
		List<String> nonOrderedPostProcessorNames = new ArrayList<>();
		// 遍历postProcessorNames,将BeanFactoryPostProcessor按实现PriorityOrdered、实现Ordered接口、普通三种区分开
		for (String ppName : postProcessorNames) {
			// 跳过已经执行过的BeanFactoryPostProcessor
			if (processedBeans.contains(ppName)) {
				// skip - already processed in first phase above
			}
			// 添加实现了PriorityOrdered接口的BeanFactoryPostProcessor到priorityOrderedPostProcessors
			else if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
			}
			// 添加实现了Ordered接口的BeanFactoryPostProcessor的beanName到orderedPostProcessorNames
			else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			} else {
				// 添加剩下的普通BeanFactoryPostProcessor的beanName到nonOrderedPostProcessorNames
				nonOrderedPostProcessorNames.add(ppName);
			}
		}
		// 对实现了PriorityOrdered接口的BeanFactoryPostProcessor进行排序
		// First, invoke the BeanFactoryPostProcessors that implement PriorityOrdered.
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		// 遍历实现了PriorityOrdered接口的BeanFactoryPostProcessor,执行postProcessBeanFactory方法
		invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);

		// 创建存放实现了Ordered接口的BeanFactoryPostProcessor的集合
		// Next, invoke the BeanFactoryPostProcessors that implement Ordered.
		List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<>(orderedPostProcessorNames.size());
		// 遍历存放实现了Ordered接口的BeanFactoryPostProcessor名字的集合
		for (String postProcessorName : orderedPostProcessorNames) {
			// 将实现了Ordered接口的BeanFactoryPostProcessor添加到集合中
			orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		// 对实现了Ordered接口的BeanFactoryPostProcessor进行排序操作
		sortPostProcessors(orderedPostProcessors, beanFactory);
		// 遍历实现了Ordered接口的BeanFactoryPostProcessor,执行postProcessBeanFactory方法
		invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);

		// 最后，创建存放普通的BeanFactoryPostProcessor的集合
		// Finally, invoke all other BeanFactoryPostProcessors.
		List<BeanFactoryPostProcessor> nonOrderedPostProcessors = new ArrayList<>(nonOrderedPostProcessorNames.size());
		// 遍历存放实现普通BeanFactoryPostProcessor名字的集合
		for (String postProcessorName : nonOrderedPostProcessorNames) {
			// 将普通的BeanFactoryPostProcessor添加到集合中
			nonOrderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}

		// 遍历普通的BeanFactoryPostProcessor，执行postProcessBeanFactory方法
		invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);

		// Clear cached merged bean definitions since the post-processors might have
		// modified the original metadata, e.g. replacing placeholders in values...
		// 清楚原数据缓存
		// 因为后置处理器可能已经修改了原始元数据，例如：替换值中的占位符
		beanFactory.clearMetadataCache();
	}

	public static void registerBeanPostProcessors(
			ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {
		// 找到所有实现了BeanPostProcessor接口的类
		String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);

		// Register BeanPostProcessorChecker that logs an info message when
		// a bean is created during BeanPostProcessor instantiation, i.e. when
		// a bean is not eligible for getting processed by all BeanPostProcessors.
		// 记录下 BeanPostProcessor 的目标计数
		// 此处+1的原因是 在此方法的最后会添加一个BeanPostProcessorChecker的类
		int beanProcessorTargetCount = beanFactory.getBeanPostProcessorCount() + 1 + postProcessorNames.length;
		// 添加BeanPostProcessorChecker（主要用于记录信息）到beanFactory中
		beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));

		// Separate between BeanPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.
		// 用于存放实现了 priorityOrdered接口的BeanPostProcessor集合
		List<BeanPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
		// 用于存放实现了 Spring内部的的BeanPostProcessor
		List<BeanPostProcessor> internalPostProcessors = new ArrayList<>();
		// 用于存放实现了 Ordered 接口的BeanPostProcessor的name集合
		List<String> orderedPostProcessorNames = new ArrayList<>();
		// 用于存放普通BeanPostProcessor的name集合
		List<String> nonOrderedPostProcessorNames = new ArrayList<>();
		// 遍历beanFactory中存在的BeanPostProcessor的集合postProcessorNames
		for (String ppName : postProcessorNames) {
			// 如果ppName对应的BeanPostProcessor实例实现了PriorityOrdered接口，则获取到ppName对应的BeanPostProcessor的实例添加到priorityOrderedPostProcessor
			if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
				priorityOrderedPostProcessors.add(pp);
				// 如果ppName对应的BeanPostProcessor实例也实现了MergedBeanDefinitionPostProcessor接口，那么将ppName对应的bean实例添加到internalPostProcessors
				if (pp instanceof MergedBeanDefinitionPostProcessor) {
					internalPostProcessors.add(pp);
				}
				// 如果ppName对应的BeanPostProcessor实例没有实现PriorityOrdered接口，但是实现了Ordered接口，那么将ppName对应的bean实例添加到orderedPostProcessorNames
			} else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			} else {
				// 否则将ppName添加到nonOrderedPostProcessorNames中
				nonOrderedPostProcessorNames.add(ppName);
			}
		}

		// First, register the BeanPostProcessors that implement PriorityOrdered.
		// 首先，对实现了PriorityOrdered接口的BeanPostProcessor实例进行排序操作
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		registerBeanPostProcessors(beanFactory, priorityOrderedPostProcessors);

		// Next, register the BeanPostProcessors that implement Ordered.
		// 注册所有实现Ordered的beanPostProcessor
		List<BeanPostProcessor> orderedPostProcessors = new ArrayList<>(orderedPostProcessorNames.size());
		for (String ppName : orderedPostProcessorNames) {
			// 根据ppName找到对应的BeanPostProcessor实例对象
			BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
			// 将实现了Ordered接口的BeanPostProcessor添加到orderedPostProcessors集合中
			orderedPostProcessors.add(pp);
			// 如果ppName对应的BeanPostProcessor实例也实现了MergedBeanDefinitionPostProcessor接口，那么将ppName对应的bean实例添加到internalPostProcessors
			if (pp instanceof MergedBeanDefinitionPostProcessor) {
				internalPostProcessors.add(pp);
			}
		}
		// 对实现了Ordered接口的BeanPostProcessor进行排序操作
		sortPostProcessors(orderedPostProcessors, beanFactory);
		// 注册实现了Ordered接口的BeanPostProcessor实例添加到beanFactory中
		registerBeanPostProcessors(beanFactory, orderedPostProcessors);

		// Now, register all regular BeanPostProcessors.
		// 创建存放没有实现PriorityOrdered和Ordered接口的BeanPostProcessor的集合
		List<BeanPostProcessor> nonOrderedPostProcessors = new ArrayList<>(nonOrderedPostProcessorNames.size());
		// 遍历集合
		for (String ppName : nonOrderedPostProcessorNames) {
			// 根据ppName找到对应的BeanPostProcessor实例对象
			BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
			// 将没有实现PriorityOrdered和Ordered接口的BeanPostProcessor添加到nonOrderedPostProcessors集合中
			nonOrderedPostProcessors.add(pp);
			// 如果ppName对应的BeanPostProcessor实例也实现了MergedBeanDefinitionPostProcessor接口，那么则将ppName对应的bean实例添加到internalPostProcessor
			if (pp instanceof MergedBeanDefinitionPostProcessor) {
				internalPostProcessors.add(pp);
			}
		}
		// 注册没有实现PriorityOrdered和Ordered的BeanPostProcessor实例添加到beanFactory中
		registerBeanPostProcessors(beanFactory, nonOrderedPostProcessors);

		// Finally, re-register all internal BeanPostProcessors.
		// 将所有实现了MergedBeanDefinitionPostProcessor类型的BeanPostProcessor进行排序操作
		sortPostProcessors(internalPostProcessors, beanFactory);
		// 注册所有实现了MergedBeanDefinitionPostProcessor类型的BeanPostProcessor到beanFactory中
		registerBeanPostProcessors(beanFactory, internalPostProcessors);

		// Re-register (在prepareBeanFactory方法中已经注册过一次)post-processor for detecting inner beans as ApplicationListeners,
		// moving it to the end of the processor chain (for picking up proxies etc).
		// 注册ApplicationListenerDetector到beanFactory中
		beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));
	}

	private static void sortPostProcessors(List<?> postProcessors, ConfigurableListableBeanFactory beanFactory) {
		// Nothing to sort?
		// 如果postProcessors的个数小于等于1，不做任何排序
		if (postProcessors.size() <= 1) {
			return;
		}
		Comparator<Object> comparatorToUse = null;
		// 判断是否是DefaultListableBeanFactory类型
		if (beanFactory instanceof DefaultListableBeanFactory) {
			// 获取设置的比较器
			comparatorToUse = ((DefaultListableBeanFactory) beanFactory).getDependencyComparator();
		}
		if (comparatorToUse == null) {
			// 如果没有设置比较器，则使用默认的OrderComparator
			comparatorToUse = OrderComparator.INSTANCE;
		}
		// 使用比较器对postProcessors进行排序
		postProcessors.sort(comparatorToUse);
	}

	/** 调用给定的BeanDefinitionRegistryPostProcessor Bean对象
	 * Invoke the given BeanDefinitionRegistryPostProcessor beans.
	 */
	private static void invokeBeanDefinitionRegistryPostProcessors(
			Collection<? extends BeanDefinitionRegistryPostProcessor> postProcessors, BeanDefinitionRegistry registry) {
		// 遍历 postProcessors
		for (BeanDefinitionRegistryPostProcessor postProcessor : postProcessors) { // 调用postProcessor 的postProcessBeanDefinitionRegistry以使得 postProcess往registry注册BeanDefinition对象
			postProcessor.postProcessBeanDefinitionRegistry(registry);
		}
	}

	/** 调用给定的 BeanFactoryPostProcessor类型Bean对象
	 * Invoke the given BeanFactoryPostProcessor beans.
	 */
	private static void invokeBeanFactoryPostProcessors(
			Collection<? extends BeanFactoryPostProcessor> postProcessors, ConfigurableListableBeanFactory beanFactory) {
		// 遍历postProcessors
		for (BeanFactoryPostProcessor postProcessor : postProcessors) { // 回调BeanFactoryPostProcessor的 postProcessBeanFactory方法 使得每个postProcessor对象都可以对beanFactory进行调整
			postProcessor.postProcessBeanFactory(beanFactory);
		}
	}

	/**
	 * Register the given BeanPostProcessor beans.
	 */
	private static void registerBeanPostProcessors(
			ConfigurableListableBeanFactory beanFactory, List<BeanPostProcessor> postProcessors) {

		// 遍历postProcessors
		for (BeanPostProcessor postProcessor : postProcessors) {
			// 将postProcessor添加到BeanFactory中的beanPostProcessors缓存
			beanFactory.addBeanPostProcessor(postProcessor);
		}
	}


	/**
	 *  当前Bean在BeanPostProcessor实例化过程中被创建时，即当前一个Bean不合适被所有BeanPostProcessor处理时，记录一个信息消息
	 * 	bean后置处理器，用来记录后置处理器实例化时的输出信息
	 * BeanPostProcessor that logs an info message when a bean is created during
	 * BeanPostProcessor instantiation, i.e. when a bean is not eligible for
	 * getting processed by all BeanPostProcessors.
	 */
	private static final class BeanPostProcessorChecker implements BeanPostProcessor {

		private static final Log logger = LogFactory.getLog(BeanPostProcessorChecker.class);

		// 当前bean工厂
		private final ConfigurableListableBeanFactory beanFactory;

		// beanPostProcessor目标数量
		private final int beanPostProcessorTargetCount;
		// 创建一个BeanPostProcessorChecker实例
		public BeanPostProcessorChecker(ConfigurableListableBeanFactory beanFactory, int beanPostProcessorTargetCount) {
			this.beanFactory = beanFactory;
			this.beanPostProcessorTargetCount = beanPostProcessorTargetCount;
		}

		/**
		 * 后置处理器的before方法，什么都不做，直接返回对象
		 *
		 * @param bean     the new bean instance
		 * @param beanName the name of the bean
		 * @return
		 */
		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName) {
			return bean;
		}

		/**
		 * 后置处理器的after方法，用来判断哪些是不需要检测的bean
		 *
		 * @param bean     the new bean instance
		 * @param beanName the name of the bean
		 * @return
		 */
		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) {
			// 1.BeanPostProcessor类型不检测
			// 2.ROLE_INFRASTRUCTURE这种类型的bean不检测（spring自己的bean）
			if (!(bean instanceof BeanPostProcessor) && !isInfrastructureBean(beanName) &&
					this.beanFactory.getBeanPostProcessorCount() < this.beanPostProcessorTargetCount) {
				if (logger.isInfoEnabled()) {
					logger.info("Bean '" + beanName + "' of type [" + bean.getClass().getName() +
							"] is not eligible for getting processed by all BeanPostProcessors " +
							"(for example: not eligible for auto-proxying)");
				}
			}
			return bean;
		}

		/**
		 * 检测当前的bean是否是spring自己的bean
		 *
		 * @param beanName
		 * @return
		 */
		private boolean isInfrastructureBean(@Nullable String beanName) {
			if (beanName != null && this.beanFactory.containsBeanDefinition(beanName)) {
				BeanDefinition bd = this.beanFactory.getBeanDefinition(beanName);
				return (bd.getRole() == RootBeanDefinition.ROLE_INFRASTRUCTURE);
			}
			return false;
		}
	}

}
