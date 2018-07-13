/**
 * Copyright (C) 2018 Lightbend Inc. <https://www.lightbend.com>
 * Copyright 2017-2018 Alexis Seigneurin.
 */

package com.lightbend.kafka.scala.streams

import org.apache.kafka.streams.kstream._
import org.apache.kafka.streams.state.KeyValueStore
import org.apache.kafka.common.utils.Bytes
import ImplicitConversions._
import FunctionConversions._
import org.apache.kafka.common.serialization.Serde

/**
 * Wraps the Java class KTable and delegates method calls to the underlying Java object.
 */
class KTableS[K, V](val inner: KTable[K, V]) {

  def filter(predicate: (K, V) => Boolean): KTableS[K, V] = {
    inner.filter(predicate(_, _))
  }

  def filter(predicate: (K, V) => Boolean,
    materialized: Materialized[K, V, KeyValueStore[Bytes, Array[Byte]]]): KTableS[K, V] = {
    inner.filter(predicate.asPredicate, materialized)
  }

  def filterNot(predicate: (K, V) => Boolean): KTableS[K, V] = {
    inner.filterNot(predicate(_, _))
  }

  def filterNot(predicate: (K, V) => Boolean,
    materialized: Materialized[K, V, KeyValueStore[Bytes, Array[Byte]]]): KTableS[K, V] = {
    inner.filterNot(predicate.asPredicate, materialized)
  }

  def mapValues[VR](mapper: V => VR): KTableS[K, VR] = {
    inner.mapValues[VR](mapper.asValueMapper)
  }

  def mapValues[VR](mapper: V => VR,
    materialized: Materialized[K, VR, KeyValueStore[Bytes, Array[Byte]]]): KTableS[K, VR] = {
    inner.mapValues[VR](mapper.asValueMapper, materialized)
  }

  def toStream: KStreamS[K, V] = inner.toStream

  def toStream[KR](mapper: (K, V) => KR): KStreamS[KR, V] = {
    inner.toStream[KR](mapper.asKeyValueMapper)
  }

  def groupBy[KR, VR](selector: (K, V) => (KR, VR))(implicit serialized: Serialized[KR, VR]): KGroupedTableS[KR, VR] = {
    inner.groupBy(selector.asKeyValueMapper, serialized)
  }

  def join[VO, VR](other: KTableS[K, VO],
    joiner: (V, VO) => VR): KTableS[K, VR] = {

    inner.join[VO, VR](other.inner, joiner.asValueJoiner)
  }

  def join[VO, VR](other: KTableS[K, VO],
    joiner: (V, VO) => VR,
    materialized: Materialized[K, VR, KeyValueStore[Bytes, Array[Byte]]]): KTableS[K, VR] = {

    inner.join[VO, VR](other.inner, joiner.asValueJoiner, materialized)
  }

  def leftJoin[VO, VR](other: KTableS[K, VO],
    joiner: (V, VO) => VR): KTableS[K, VR] = {

    inner.leftJoin[VO, VR](other.inner, joiner.asValueJoiner)
  }

  def leftJoin[VO, VR](other: KTableS[K, VO],
    joiner: (V, VO) => VR,
    materialized: Materialized[K, VR, KeyValueStore[Bytes, Array[Byte]]]): KTableS[K, VR] = {

    inner.leftJoin[VO, VR](other.inner, joiner.asValueJoiner, materialized)
  }

  def outerJoin[VO, VR](other: KTableS[K, VO],
    joiner: (V, VO) => VR): KTableS[K, VR] = {

    inner.outerJoin[VO, VR](other.inner, joiner.asValueJoiner)
  }

  def outerJoin[VO, VR](other: KTableS[K, VO],
    joiner: (V, VO) => VR,
    materialized: Materialized[K, VR, KeyValueStore[Bytes, Array[Byte]]]): KTableS[K, VR] = {

    inner.outerJoin[VO, VR](other.inner, joiner.asValueJoiner, materialized)
  }

  def oneToManyJoin[V0, KL, VL, KR, VR](other: KTableS[KR, VR],
                                    keyExtractor: ValueMapper[VR, KL],
                                    joiner: ValueJoiner[VL, VR, V0],
                                    materialized: Materialized[KR, V0, KeyValueStore[Bytes, Array[Byte]]],
                                    thisKeySerde: Serde[KL],
                                    otherKeySerde: Serde[KR],
                                    otherValueSerde: Serde[VR],
                                    joinedValueSerde: Serde[V0]
                                    ): KTableS[KR, V0] = {
    inner.oneToManyJoin(other.inner,
      keyExtractor,
      joiner,
      materialized,
      thisKeySerde,
      otherKeySerde,
      otherValueSerde,
      joinedValueSerde)
  }

  def queryableStoreName: String =
    inner.queryableStoreName
}